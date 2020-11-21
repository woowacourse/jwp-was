package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorsTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorsTest.class);
    private static AtomicInteger COUNTER = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(100);

        StopWatch sw = new StopWatch();
        sw.start();
        for (int i = 0; i < 100; i++) {
            es.execute(() -> {
                int idx = COUNTER.addAndGet(1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LOGGER.info("Thread {}", idx);
            });
        }
        sw.stop();

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);
        LOGGER.info("Total Elaspsed: {}", sw.getTotalTimeSeconds());
    }
}

