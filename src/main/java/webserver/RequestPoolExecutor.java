package webserver;

import java.util.concurrent.ThreadPoolExecutor;

public class RequestPoolExecutor {
    private final ThreadPoolExecutor executor;

    public RequestPoolExecutor(ThreadPoolExecutor executor) {
        this.executor = executor;
    }

    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}
