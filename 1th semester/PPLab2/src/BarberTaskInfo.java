import java.math.BigInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Задача для парикмахера
public class BarberTaskInfo {

    private int clientId; // Номер посетителя
    private int factorialNumber; // Число для вычисления факториала
    private Integer bitCountResult = null; // Результат вычислений
    private Lock resultWaitingLock = new ReentrantLock();
    private Condition resultWaitingCondition; // Объект условия получения результата вычислений

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
    
    // Запуск решения задачи
    // Задача заключается в том, чтобы найти количество бит в числе, равном факториалу входного параметра
    public void processTask() {
        BigInteger x = BigInteger.ONE;
        for (int i = 2; i < this.factorialNumber; i++) {
            x = x.multiply(BigInteger.valueOf(i));
        }
        bitCountResult = x.bitCount();
        System.out.println("Парикмахер завершил работу с клиентом #" + clientId + "; результат: " + bitCountResult);
    }

}