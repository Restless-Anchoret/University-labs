
public class Input {

    private final Function function;
    private final double a, b;
    private final int n;

    public Input(Function function, double a, double b, int n) {
        this.function = function;
        this.a = a;
        this.b = b;
        this.n = n;
    }

    public Function getFunction() {
        return function;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public int getN() {
        return n;
    }

    @Override
    public String toString() {
        return "Input{"
                + "a=" + a
                + ", b=" + b
                + ", n=" + n
                + '}';
    }

}
