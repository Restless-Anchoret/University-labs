
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Barbershop {

    private Queue<BarberTaskInfo> tasksQueue = new LinkedList<>();
    private Semaphore semaphore;
    private Lock clientsWaitingLock = new ReentrantLock();
    private Condition clientsWaitingCondition;
    private Thread barberThread;
    
    public Barbershop(int capacity) {
        semaphore = new Semaphore(capacity);
        clientsWaitingCondition = clientsWaitingLock.newCondition();
        barberThread = new Thread(new BarberLifecycle());
    }
    
    public void startWork() {
        barberThread.start();
    }
    
    public Integer takeService(int clientId, int factorialNumber) throws InterruptedException {
        boolean enteringSuccess = semaphore.tryEnter();
        if (!enteringSuccess) {
            System.out.println("Client #" + clientId + " failed to enter barbershop");
            return null;
        }
        
        BarberTaskInfo barberTaskInfo = new BarberTaskInfo(clientId, factorialNumber);
        clientsWaitingLock.lock();
        try {
            tasksQueue.add(barberTaskInfo);
            System.out.println("Client #" + clientId + " entered to barbershop successfully with input number: " + factorialNumber);
            clientsWaitingCondition.signal();
        } finally {
            clientsWaitingLock.unlock();
        }
        
        barberTaskInfo.getResultWaitingLock().lock();
        try {
            while (barberTaskInfo.getBitCountResult() == null) {
                barberTaskInfo.getResultWaitingCondition().await();
            }
        } finally {
            barberTaskInfo.getResultWaitingLock().unlock();
        }
        
        semaphore.leave();
        System.out.println("Client #" + clientId + " left barbershop with result: " + barberTaskInfo.getBitCountResult());
        return barberTaskInfo.getBitCountResult();
    }
    
    private class BarberLifecycle implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    BarberTaskInfo currentBarberTaskInfo = null;
                    clientsWaitingLock.lock();
                    try {
                        while ((currentBarberTaskInfo = tasksQueue.poll()) == null) {
                            clientsWaitingCondition.await();
                        }
                    } finally {
                        clientsWaitingLock.unlock();
                    }
                    
                    currentBarberTaskInfo.processTask();
                    
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