public class Result {

    private final double result;
    private final long totalTime;

    public Result(double result, long totalTime) {
        this.result = result;
        this.totalTime = totalTime;
    }

    public double getResult() {
        return result;
    }

    public long getTotalTime() {
        return totalTime;
    }

    @Override
    public String toString() {
        return "Result{" + "result=" + result + ", totalTime=" + totalTime + '}';
    }
    
}