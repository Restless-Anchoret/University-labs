
import java.util.ConcurrentModificationException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Semaphore {

    private final int maxThreadsQuantity;
    private int enteredThreads = 0;
    private Lock lock;
    private Condition enteringAllowedCondition;

    public Semaphore(int maxThreadsQuantity) {
        this.maxThreadsQuantity = maxThreadsQuantity;
        lock = new ReentrantLock();
        enteringAllowedCondition = lock.newCondition();
    }

    public int getMaxThreadsQuantity() {
        return maxThreadsQuantity;
    }
    
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