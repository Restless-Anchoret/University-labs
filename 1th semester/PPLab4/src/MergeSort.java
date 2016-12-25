public class MergeSort {

    private static final int MIN_ELEMENTS_PER_THREAD = 1000;
    
    // API-метод для сортировки массива с автоматическим определением оптимального
    // количества потоков для распараллеливания
    public void mergeSort(int[] array) {
        int threads = countOptimalThreadsQuantity(array.length);
        mergeSort(array, threads);
    }
    
    // API-метод для сортировки массива в заданном количестве потоков
    public void mergeSort(int[] array, int threads) {
        int[] extraArray = new int[array.length];
        mergeSort(array, extraArray, 0, array.length - 1, threads);
    }
    
    // Сортировка слиянием участка массива в нескольких потоках
    private void mergeSort(int[] array, int[] extraArray, int from, int to, int threads) {
        if (from == to) {
            return;
        }
        int mid = (from + to) / 2;
        if (threads == 1) {
            // Если нужно выполнить сортировку в одном потоке, выполняем её в текущем потоке целиком
            mergeSort(array, extraArray, from, mid, threads);
            mergeSort(array, extraArray, mid + 1, to, threads);
            merge(array, extraArray, from, mid, to);
        } else {
            // Определяем количество потоков для сортировки правой и левой части участка массива
            int threadsForLeftPart = threads / 2;
            int threadsForRightPart = threads - threadsForLeftPart;
            
            // Запускаем в отдельном потоке сортировку левой части участка массива
            Runnable sortLeftPartTask = () -> mergeSort(array, extraArray, from, mid, threadsForLeftPart);
            Thread sortLeftPartThread = new Thread(sortLeftPartTask);
            sortLeftPartThread.start();
            
            // Выполняем в текущем потоке сортировку правой части участка массива
            mergeSort(array, extraArray, mid + 1, to, threadsForRightPart);
            
            // Дожидаемся окончания сортировки левой части участка массива
            try {
                sortLeftPartThread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
            
            // Сливаем отсортированные участки массива в текущем потоке
            merge(array, extraArray, from, mid, to);
        }
    }
    
    // Слияние двух отсортированных участков массива
    private void merge(int[] array, int[] extraArray, int from, int mid, int to) {
        for (int i = from; i <= to; i++) {
            extraArray[i] = array[i];
        }
        int i = from;
        int j = mid + 1;
        int k = from;
        while (i <= mid && j <= to) {
            if (extraArray[i] <= extraArray[j]) {
                array[k] = extraArray[i];
                i++;
            } else {
                array[k] = extraArray[j];
                j++;
            }
            k++;
        }
        while (i <= mid) {
            array[k] = extraArray[i];
            i++;
            k++;
        }
        while (j <= to) {
            array[k] = extraArray[j];
            j++;
            k++;
        }
    }
    
    // Определение оптимального количества потоков для распараллеливания
    private int countOptimalThreadsQuantity(int n) {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("Available processors: " + availableProcessors);
        int maxThreadsQuantity = Math.max(n / MIN_ELEMENTS_PER_THREAD, 1);
        int threadsQuantity = Math.min(availableProcessors, maxThreadsQuantity);
        System.out.println("Threads quantity: " + threadsQuantity);
        return threadsQuantity;
    }
    
}