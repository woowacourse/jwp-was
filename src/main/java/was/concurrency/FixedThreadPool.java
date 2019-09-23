package was.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPool implements ThreadPool {
    private final ExecutorService pool;

    public FixedThreadPool(int countOfThreads) {
        this.pool = Executors.newFixedThreadPool(countOfThreads);
    }

    @Override
    public void execute(Runnable task) {
        pool.execute(task);
    }
}
