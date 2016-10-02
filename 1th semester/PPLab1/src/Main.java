
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class Main {

    private static final int MIN_OPERATION_FOR_ONE_THREAD = 1000;
    private static final int RUNNING_TIMES = 30;

    public static void main(String[] args) {
        Input input = getInput();
        System.out.println("Input = " + input + "\n");
        List<Integer> threadQuantities = Arrays.asList(0, 1, 2, 4, 8);
        Map<Integer, Long> mapTimes = new TreeMap<>();
        for (int threadsQuantity: threadQuantities) {
            mapTimes.put(threadsQuantity, makeStatistics(input, threadsQuantity));
        }
        int optimalThreadsQuantity = countThreadsQuantityForInput(input);
        long optimalTime = makeStatistics(input, optimalThreadsQuantity);
        for (Map.Entry<Integer, Long> entry: mapTimes.entrySet()) {
            System.out.println("Threads quantity: " + entry.getKey() + " - " + entry.getValue() + " milliseconds.");
        }
        System.out.println("Dinamically counted threads quantity: " + optimalThreadsQuantity + " - " + optimalTime + " milliseconds");
    }

    // Входные параметры
    private static Input getInput() {
        // Функция, a, b, N
        return new Input(x -> 3 * x * x, -100, 100, 10000000);
    }
    
    private static long makeStatistics(Input input, int threadsQuantity) {
        System.out.println("Threads for creation quantity: " + threadsQuantity);
        long totalTime = 0;
        for (int i = 1; i <= RUNNING_TIMES; i++) {
            System.out.println("Running #" + i);
            Result result = new ParallelSimpsonMethod().countResultForInput(input, threadsQuantity);
            System.out.println("Result = " + result);
            System.out.println();
            totalTime += result.getTotalTime();
        }
        System.out.println();
        return totalTime / RUNNING_TIMES;
    }

    // Определение оптимального количества потоков для распараллеливания
    private static int countThreadsQuantityForInput(Input input) {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("Available processors: " + availableProcessors);
        int maxThreadsQuantity = Math.max((input.getN() + 1) / MIN_OPERATION_FOR_ONE_THREAD, 1);
        int threadsQuantity = Math.min(availableProcessors, maxThreadsQuantity);
        System.out.println("Threads quantity: " + threadsQuantity);
        return threadsQuantity;
    }

}
