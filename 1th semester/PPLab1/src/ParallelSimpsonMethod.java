
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ParallelSimpsonMethod {

    public Result countResultForInput(Input input, int threadsQuantity) {
        // Момент начала работы метода, общее время работы
        long start = System.currentTimeMillis();
        
        // Единая глобальная переменная с результатом
        SynchronizedDoubleValue result = new SynchronizedDoubleValue();
        
        if (threadsQuantity == 0) {
            SimpsonTask simpsonTask = new SimpsonTask(input, 0, input.getN(), result);
            simpsonTask.run();
        } else {
            // Задачи и потоки
            List<SimpsonTask> simpsonTasks = new ArrayList<>(threadsQuantity);
            List<Thread> threads = new ArrayList<>(threadsQuantity);
            
            // Создание задач для каждого потока и самих потоков
            int forOneThread = (input.getN() + 1) / threadsQuantity;
            for (int i = 0; i < threadsQuantity; i++) {
                int pointFrom = i * forOneThread;
                int pointTo = (i + 1) * forOneThread - 1;
                if (i == threadsQuantity - 1) {
                    pointTo = input.getN();
                }
                SimpsonTask simpsonTask = new SimpsonTask(input, pointFrom, pointTo, result);
                simpsonTasks.add(simpsonTask);
                threads.add(new Thread(simpsonTask));
            }

            // Запуск потоков
            for (Thread thread : threads) {
                thread.start();
            }

            // Ожидание завершения всех потоков
            try {
                for (int i = 0; i < threadsQuantity; i++) {
                    threads.get(i).join();
                }
            } catch (InterruptedException exception) {
                exception.printStackTrace();
                throw new RuntimeException(exception);
            }

            // Вывод времени, затраченного на работу каждого потока
            for (int i = 0; i < threadsQuantity; i++) {
                System.out.println("Thread #" + i + " finished in "
                        + simpsonTasks.get(i).getMilliseconds() + " milliseconds");
            }
        }

        // Расчёт окончательного результата и времени работы
        double h = (input.getB() - input.getA()) / input.getN();
        double factor = h / 3.0;
        double resultValue = factor * result.getValue();
        long totalTime = System.currentTimeMillis() - start;
        System.out.println("Total time is " + totalTime + " milliseconds");
        return new Result(resultValue, totalTime);
    }

    // Задача для одного потока
    private static class SimpsonTask implements Runnable {

        private final Input input;
        private final int pointFrom, pointTo;
        // Глобальная переменная с результатом
        private final SynchronizedDoubleValue result;
        private long milliseconds;

        public SimpsonTask(Input input, int pointFrom, int pointTo, SynchronizedDoubleValue result) {
            this.input = input;
            this.pointFrom = pointFrom;
            this.pointTo = pointTo;
            this.result = result;
        }

        public long getMilliseconds() {
            return milliseconds;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            double localResult = 0;
            double step = (input.getB() - input.getA()) / input.getN(); // Шаг сетки
            for (int i = pointFrom; i <= pointTo; i++) {
                double x = input.getA() + step * i; // Значение xi
                double f = input.getFunction().getValue(x); // Значение функции в точке xi
                double increment = getFactorForI(i) * f; // Приращение к общему результату
                localResult += increment;
            }
            result.addNumberToValue(localResult);
            long end = System.currentTimeMillis();
            milliseconds = end - start;
        }

        // Выбор коэффициента у общей суммы (1, 2 или 4)
        private int getFactorForI(int i) {
            if (i == 0 || i == input.getN()) {
                return 1;
            } else if (i % 2 == 0) {
                return 2;
            } else {
                return 4;
            }
        }
    }

    // Double-значение с синхронизованным доступом
    private static class SynchronizedDoubleValue {

        private Lock lock = new ReentrantLock();
        private double value = 0.0;

        public double getValue() {
            lock.lock();
            double currentValue = value;
            lock.unlock();
            return currentValue;
        }

        public void addNumberToValue(double number) {
            lock.lock();
            value += number;
            lock.unlock();
        }
    }

}
