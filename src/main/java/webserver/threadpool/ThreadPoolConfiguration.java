package webserver.threadpool;

import java.util.concurrent.TimeUnit;

public class ThreadPoolConfiguration {
    private static final int THREAD_SIZE = 10;
    private static final int KEEP_ALIVE_TIME = 0;
    private static final TimeUnit TIME_UNIT = TimeUnit.MICROSECONDS;

    private final int threadSize;
    private final long keepAliveTime;
    private final TimeUnit timeUnit;

    public ThreadPoolConfiguration() {
        this(THREAD_SIZE, KEEP_ALIVE_TIME, TIME_UNIT);
    }

    private ThreadPoolConfiguration(int threadSize, long keepAliveTime, TimeUnit timeUnit) {
        this.threadSize = threadSize;
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
    }

    public int getThreadSize() {
        return threadSize;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
