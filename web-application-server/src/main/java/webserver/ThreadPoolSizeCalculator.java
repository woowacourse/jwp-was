package webserver;

public class ThreadPoolSizeCalculator {
    private static final int NUMBER_OF_AVAILABLE_CORES = Runtime.getRuntime().availableProcessors();
    private static final double CPU_UTILIZATION = 1.0;
    private static final int WAIT_TIME = 10;
    private static final int SERVICE_TIME = 1;

    public static int getThreadPoolSize() {
        return (int)(NUMBER_OF_AVAILABLE_CORES * CPU_UTILIZATION * (1 + WAIT_TIME / SERVICE_TIME));
    }
}
