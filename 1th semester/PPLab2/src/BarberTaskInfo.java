
import java.math.BigInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BarberTaskInfo {

    private int clientId;
    private int factorialNumber;
    private Integer bitCountResult = null;
    private Lock resultWaitingLock = new ReentrantLock();
    private Condition resultWaitingCondition;

    public BarberTaskInfo(int clientId, int factorialNumber) {
        this.clientId = clientId;
        this.factorialNumber = factorialNumber;
        resultWaitingCondition = resultWaitingLock.newCondition();
    }

    public int getClientId() {
        return clientId;
    }

    public int getFactorialNumber() {
        return factorialNumber;
    }

    public Lock getResultWaitingLock() {
        return resultWaitingLock;
    }

    public Condition getResultWaitingCondition() {
        return resultWaitingCondition;
    }

    public Integer getBitCountResult() {
        return bitCountResult;
    }
    
    public void processTask() {
        BigInteger x = BigInteger.ONE;
        for (int i = 2; i < 50_000; i++) {
            x = x.multiply(BigInteger.valueOf(i));
        }
        bitCountResult = x.bitCount();
        System.out.println("Barber finished task for client #" + clientId + "; the result is: " + bitCountResult);
    }

}