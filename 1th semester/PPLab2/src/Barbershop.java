import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Контейнер с очередью посетителей и парикмахером
public class Barbershop {

    private Queue<BarberTaskInfo> tasksQueue = new LinkedList<>(); // Очередь задач на выполнение
    private Semaphore semaphore;
    private Lock clientsWaitingLock = new ReentrantLock();
    private Condition clientsWaitingCondition; // Объект условия появления новых клиентов
    private Thread barberThread; // Поток, в котором выполняет задачи парикмахер

    public Barbershop(int capacity) {
        semaphore = new Semaphore(capacity);
        clientsWaitingCondition = clientsWaitingLock.newCondition();
        barberThread = new Thread(new BarberLifecycle());
    }

    // Запуск потока парикмахера
    public void startWork() {
        barberThread.start();
    }

    // Добавление в очередь запроса и ожидание результата
    public Integer takeService(int clientId, int factorialNumber) throws InterruptedException {
        // Попытка пройти через семафор
        boolean enteringSuccess = semaphore.tryEnter();
        if (!enteringSuccess) {
            System.out.println("Клиент #" + clientId + " не смог войти в парикмахерскую. Клиентов в очереди: " + tasksQueue.size());
            return null;
        }

        // Добавление задачи в очередь
        BarberTaskInfo barberTaskInfo = new BarberTaskInfo(clientId, factorialNumber);
        clientsWaitingLock.lock();
        try {
            tasksQueue.add(barberTaskInfo);
            System.out.println("Клиент #" + clientId + " вошел в парикмахерскую с входным номером: "
                    + factorialNumber + ". Клиентов в очереди: " + tasksQueue.size());
            clientsWaitingCondition.signal();
        } finally {
            clientsWaitingLock.unlock();
        }

        // Ожидание выполнения задачи парикмахером
        barberTaskInfo.getResultWaitingLock().lock();
        try {
            while (barberTaskInfo.getBitCountResult() == null) {
                barberTaskInfo.getResultWaitingCondition().await();
            }
        } finally {
            barberTaskInfo.getResultWaitingLock().unlock();
        }

        // Выход через семафор и возврат результата
        semaphore.leave();
        System.out.println("Клиент #" + clientId + " покинул парикмахерскую с результатом: " 
                + barberTaskInfo.getBitCountResult()  + ". Клиентов в очереди: " + tasksQueue.size());
        return barberTaskInfo.getBitCountResult();
    }

    // Жизненный цикл парикмахера
    private class BarberLifecycle implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    // Ожидание получения очередной задачи из очереди
                    BarberTaskInfo currentBarberTaskInfo = null;
                    clientsWaitingLock.lock();
                    try {
                        while ((currentBarberTaskInfo = tasksQueue.poll()) == null) {
                            clientsWaitingCondition.await();
                        }
                    } finally {
                        clientsWaitingLock.unlock();
                    }

                    // Выполнение задачи
                    currentBarberTaskInfo.processTask();

                    // Оповещение посетителя о выполнении задачи
                    currentBarberTaskInfo.getResultWaitingLock().lock();
                    try {
                        currentBarberTaskInfo.getResultWaitingCondition().signal();
                    } finally {
                        currentBarberTaskInfo.getResultWaitingLock().unlock();
                    }
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException("Unexpected InterruptedException", ex);
            }
        }

    }

}
