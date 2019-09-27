package server.concurrency;

public interface ThreadPool {
    void execute(Runnable task);
}
