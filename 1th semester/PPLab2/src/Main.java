
import java.util.Random;

public class Main {

    private static final int BARBERSHOP_CAPACITY = 10;
    private static final long CLIENTS_ENTERING_INTERVAL = 2000;
    private static final int FACTORIAL_NUMBER_MIN_EDGE = 30_000;
    private static final int FACTORIAL_NUMBER_MAX_EDGE = 100_000;
    
    public static void main(String[] args) {
        Barbershop barbershop = new Barbershop(BARBERSHOP_CAPACITY);
        barbershop.startWork();
        int clientsCounter = 0;
        Random random = new Random();
        while (true) {
            clientsCounter++;
            int clientId = clientsCounter;
            int factorialNumber = FACTORIAL_NUMBER_MIN_EDGE +
                    random.nextInt(FACTORIAL_NUMBER_MAX_EDGE - FACTORIAL_NUMBER_MIN_EDGE + 1);
            Thread clientThread = new Thread(() -> {
                try {
                    barbershop.takeService(clientId, factorialNumber);
                } catch (InterruptedException ex) {
                    throw new RuntimeException("Unexpected InterruptedException", ex);
                }
            });
            clientThread.start();
            try {
                Thread.sleep(CLIENTS_ENTERING_INTERVAL);
            } catch (InterruptedException ex) {
                throw new RuntimeException("Unexpected InterruptedException", ex);
            }
        }
    }
    
}