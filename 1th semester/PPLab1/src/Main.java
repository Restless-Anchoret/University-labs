
public class Main {

    public static void main(String[] args) {
        Input input = getInput();
        System.out.println("Input = " + input);
        double result = new ParallelSimpsonMethod().countResultForInput(input);
        System.out.println("Result = " + result);
    }

    // Входные параметры
    private static Input getInput() {
        // Функция, a, b, N
        return new Input(x -> 3 * x * x, -100, 100, 10000000);
    }

}
