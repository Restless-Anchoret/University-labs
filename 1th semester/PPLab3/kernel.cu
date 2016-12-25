
#include "cuda_runtime.h"
#include "device_launch_parameters.h"

#include <stdio.h>
#include <iostream>
#include <string>
#include <ctime>

using namespace std;

#define MATRIX_DIM 1000
#define EPS 0.000001
#define THREADS_NUM 64

typedef struct {
	int dim;
	double* elements;
} Matrix;

cudaError_t addWithCuda(int *c, const int *a, const int *b, unsigned int size);
cudaError_t getDet(const Matrix matrix);


// Поиск ненулевого столбца, начиная с позиции start в строке с номером row
__global__ void findNonZeroColumn(Matrix m, int row, int * nonZeroCol) {
	if (*nonZeroCol != -1) {
		int col = blockIdx.x * blockDim.x + threadIdx.x + row;
		if (col < m.dim) {
			double elem = m.elements[row * m.dim + col];
			if (*nonZeroCol != -1 && fabs(elem) > EPS) {
				*nonZeroCol = col;
			}
		}
	}
}

// Прибавляем к столбцу другой без 0 на диагонали
__global__ void normalizeColumn(Matrix m, int to, int from) {
	int row = blockIdx.x * blockDim.x + threadIdx.x;
	if (row < m.dim) {
		m.elements[m.dim*row + to] += m.elements[m.dim*row + from];
	}
}

// Делим строку на элемент на главной дагонали
__global__ void divideRow(Matrix m, int row) {
	int col = blockIdx.x*blockDim.x + threadIdx.x;
	if (col < m.dim) {
		m.elements[m.dim*row + col] = m.elements[m.dim*row + col] / m.elements[m.dim*row + row];
	}
}

// Вычитаем строку diag из всех строк матрицы ниже diag, умножая их на элемент в столбце diag
__global__ void updateMatrix(Matrix m, int diag) {
	int subdim = m.dim - diag - 1;
	int index = blockIdx.x*blockDim.x + threadIdx.x;
	int row = index / subdim + diag + 1;
	int col = index % subdim + diag + 1;
	if (row < m.dim && col < m.dim) {
		double val = m.elements[m.dim*row + col];
		double diff = m.elements[m.dim*row + diag] * m.elements[m.dim*diag + col];
		m.elements[m.dim*row + col] = val - diff;
	}
}

// Зануляем столбец матрицы ниже заданного элемента
__global__ void putZeros(Matrix m, int diag) {
	int row = blockIdx.x*blockDim.x + threadIdx.x + diag + 1;
	if (row < m.dim) {
		m.elements[m.dim*row + diag] = 0;
	}
}

int main()
{
	srand(time(0));
	unsigned int beginTime = clock();
	Matrix m;
	m.dim = MATRIX_DIM;

	m.elements = new double[MATRIX_DIM*MATRIX_DIM];
	for (int i = 0; i < MATRIX_DIM; i++) {
		for (int j = 0; j < MATRIX_DIM; j++) {
			int index = i*MATRIX_DIM + j;
			if (i == j) {
				m.elements[index] = 1;
			}
			else if (j < i) {
				m.elements[index] = rand();
			}
			else {
				m.elements[index] = 0;
			}
		}
	}
	
	getDet(m);

	cout << "Total time " << clock() - beginTime << endl << endl;

	system("pause");
	return 0;
}

// Получить элемент из матрицы Cuda
double getElemFormcMatrix(Matrix cm, int x, int y) {
	double tmp;
	cudaMemcpy(&tmp, cm.elements + x*cm.dim + y, sizeof(double), cudaMemcpyDeviceToHost);
	return tmp;
}

// Перевести матрицу из оперативной памяти в матрицу Cuda
Matrix getCudaMatrixByHostMatrix(Matrix matrix) {
	Matrix cMatrix;
	cMatrix.dim = matrix.dim;
	size_t size = matrix.dim * matrix.dim * sizeof(double);
	cudaMalloc(&cMatrix.elements, size);
	cudaMemcpy(cMatrix.elements, matrix.elements, size, cudaMemcpyHostToDevice);
	return cMatrix;
}

// Вычисление определителя
cudaError_t getDet(const Matrix matrix)
{
	Matrix cMatrix = getCudaMatrixByHostMatrix(matrix);
	double det = 1;
	cudaError_t cudaStatus;

	int *nonZeroColumn;
	cudaMalloc(&nonZeroColumn, sizeof(int));


	// Choose which GPU to run on, change this on a multi-GPU system.
	cudaStatus = cudaSetDevice(0);
	if (cudaStatus != cudaSuccess) {
		fprintf(stderr, "cudaSetDevice failed!  Do you have a CUDA-capable GPU installed?");
	}

	double diagElem;

	for (int i = 0; i < MATRIX_DIM; i++) {
		diagElem = getElemFormcMatrix(cMatrix, i, i);

		if (fabs(diagElem) < EPS) {
			int hNonZeroCol = -1;
			int blockNum = ceil((double)(MATRIX_DIM - i) / THREADS_NUM);
			cudaMemset(&nonZeroColumn, -1, sizeof(int));
			findNonZeroColumn << <blockNum, THREADS_NUM >> >(cMatrix, i, nonZeroColumn);
			cudaDeviceSynchronize();
			cudaMemcpy(&hNonZeroCol, nonZeroColumn, sizeof(int), cudaMemcpyDeviceToHost);
			diagElem = getElemFormcMatrix(cMatrix, i, hNonZeroCol);
			if (hNonZeroCol != -1 && fabs(diagElem) > EPS) {
				cout << "hNonZeroCol " << hNonZeroCol << endl;
				normalizeColumn << <ceil((double)MATRIX_DIM / THREADS_NUM), THREADS_NUM >> >(cMatrix, i, hNonZeroCol);
				cudaDeviceSynchronize();
			} else {
				det = 0;
				cout << "No non zero " << hNonZeroCol << endl;
				break;
			}
		}

		det = det * diagElem;

		if (i != MATRIX_DIM - 1) {
			divideRow << <ceil((double)MATRIX_DIM / THREADS_NUM), THREADS_NUM >> >(cMatrix, i);
			cudaDeviceSynchronize();
			
			int columsNum = MATRIX_DIM - i - 1; // число столбцов для обработки
			int rowNum = columsNum; // число строк для обработки
			int threads = columsNum < THREADS_NUM ? columsNum : THREADS_NUM;
			int blocks = ceil(double(rowNum * columsNum) / threads);
			
			updateMatrix << < blocks, threads >> >(cMatrix, i);
			cudaDeviceSynchronize();
			
			threads = rowNum < THREADS_NUM ? rowNum : THREADS_NUM;
			blocks = ceil(double(rowNum) / threads);
			
			putZeros << < blocks, threads >> >(cMatrix, i);
			cudaDeviceSynchronize();
		}
	}

	cout << "Det = " << det << endl;

	// cudaDeviceSynchronize waits for the kernel to finish, and returns
	// any errors encountered during the launch.
	cudaStatus = cudaDeviceSynchronize();
	if (cudaStatus != cudaSuccess) {
		fprintf(stderr, "cudaDeviceSynchronize returned error code %d \n", cudaStatus);
	}

	cudaFree(&nonZeroColumn);
	cudaFree(cMatrix.elements);
	return cudaStatus;
}