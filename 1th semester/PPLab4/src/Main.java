import java.util.Random;

public class Main {

    private static final int NUMBERS_BOUND = 100_000;
    
    public static void main(String[] args) {
        // Цикл с количеством элементов в сортируемом массиве
        for (int arrayLength: new int[]{100_000, 1_000_000, 10_000_000}) {
            // Цикл с количеством потоков для распараллеливания
            // null - означает, что количество потоков нужно определить автоматически
            for (Integer threads: new Integer[]{1, 2, 4, 8, null}) {
                test(arrayLength, threads);
            }
            System.out.println();
        }
    }
    
    // Тестирование сортировки слиянием с заданной длиной массива и количеством потоков
    private static void test(int arrayLength, Integer threads) {
        System.out.println("Array length: " + arrayLength + "; threads quantity: " + threads);
        int[] array = generateArray(arrayLength, NUMBERS_BOUND);
        long start = System.currentTimeMillis();
        MergeSort mergeSort = new MergeSort();
        if (threads != null) {
            mergeSort.mergeSort(array, threads);
        } else {
            mergeSort.mergeSort(array);
        }
        long finish = System.currentTimeMillis();
        boolean arrayIsSorted = checkArrayIsSorted(array);
        System.out.println("Time: " + (finish - start) + " milliseconds. Array is sorted: " + arrayIsSorted);
    }
    
    // Генерация массива
    private static int[] generateArray(int n, int bound) {
        int[] array = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(bound);
        }
        return array;
    }
    
    // Автоматическая проверка того, отсортирован массив или нет
    private static boolean checkArrayIsSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }
    
}