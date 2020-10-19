package webserver.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class RequestThreadPoolExecutor {
    private final ThreadPoolExecutor threadPoolExecutor;
    private final ThreadPoolConfiguration configuration;

    public RequestThreadPoolExecutor() {
        this.configuration = new ThreadPoolConfiguration();
        this.threadPoolExecutor = new ThreadPoolExecutor(configuration.getThreadSize(), configuration.getThreadSize(),
                configuration.getKeepAliveTime(), configuration.getTimeUnit(), new LinkedBlockingQueue<Runnable>());
    }

    public void execute(Runnable task) {
        threadPoolExecutor.execute(task);
    }
}
