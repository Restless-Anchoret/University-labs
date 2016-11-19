import java.util.Random;

public class Main {

    private static final int BARBERSHOP_CAPACITY = 10; // Максимальное количество посетителей в парикмахерской
    private static final long CLIENTS_ENTERING_INTERVAL = 2000; // Интервал времени между появлениями новых посетителей
    
    // Границы интервала, которому принадлежит входной параметр задачи для выполнения
    private static final int FACTORIAL_NUMBER_MIN_EDGE = 20_000;
    private static final int FACTORIAL_NUMBER_MAX_EDGE = 100_000;
    
    public static void main(String[] args) {
        // Создание парикмахерской и запуск её работы
        Barbershop barbershop = new Barbershop(BARBERSHOP_CAPACITY);
        barbershop.startWork();
        
        int clientsCounter = 0;
        Random random = new Random();
        
        // Запуск процесса генерации новых посетителей
        while (true) {
            clientsCounter++;
            int clientId = clientsCounter;
            
            // Генерация входного параметра задачи
            int factorialNumber = FACTORIAL_NUMBER_MIN_EDGE +
                    random.nextInt(FACTORIAL_NUMBER_MAX_EDGE - FACTORIAL_NUMBER_MIN_EDGE + 1);
            
            // Создание потока посетителя, которому необходимо получить результат решения задачи
            Thread clientThread = new Thread(() -> {
                try {
                    barbershop.takeService(clientId, factorialNumber);
                } catch (InterruptedException ex) {
                    throw new RuntimeException("Unexpected InterruptedException", ex);
                }
            });
            clientThread.start();
            
            // Ожидание до появления нового посетителя
            try {
                Thread.sleep(CLIENTS_ENTERING_INTERVAL);
            } catch (InterruptedException ex) {
                throw new RuntimeException("Unexpected InterruptedException", ex);
            }
        }
    }
    
}