import java.util.ConcurrentModificationException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Semaphore {

    private final int maxThreadsQuantity; // Максимальное количество потоков, которые могут пройти через семафор
    private int enteredThreads = 0; // Количество потоков, которые прошли через семафор на текущий момент
    private Lock lock;
    private Condition enteringAllowedCondition; // Объект условия разрешения входа через семафор

    public Semaphore(int maxThreadsQuantity) {
        this.maxThreadsQuantity = maxThreadsQuantity;
        lock = new ReentrantLock();
        enteringAllowedCondition = lock.newCondition();
    }

    public int getMaxThreadsQuantity() {
        return maxThreadsQuantity;
    }
    
    // Попытка пройти через семафор
    public boolean tryEnter() {
        lock.lock();
        try {
            if (enteredThreads == maxThreadsQuantity) {
                return false;
            }
            enteredThreads++;
            return true;
        } finally {
            lock.unlock();
        }
    }
    
    // Вход через семафор
    public void enter() throws InterruptedException {
        lock.lock();
        try {
            while (enteredThreads == maxThreadsQuantity) {
                enteringAllowedCondition.await();
            }
            enteredThreads++;
        } finally {
            lock.unlock();
        }
    }
    
    // Выход через семафор
    public void leave() {
        lock.lock();
        try {
            if (enteredThreads == 0) {
                throw new ConcurrentModificationException();
            }
            enteredThreads--;
            enteringAllowedCondition.signal();
        } finally {
            lock.unlock();
        }
    }
    
}