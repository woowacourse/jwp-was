package learningtest;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ExecutorServiceTest {
    private static final long SLEEPING_TIME_IN_MILLISECONDS = 3000;
    private static final Runnable sleep = () -> {
        try {
            TimeUnit.MILLISECONDS.sleep(SLEEPING_TIME_IN_MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    @Test
    void newFixedThreadPool_() throws InterruptedException {
        int nThreads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);

        int moreThanNThreads = nThreads + 10;
        for (int i = 0; i < moreThanNThreads; i++) {
            executor.execute(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.printf("[%s] begin\n", threadName);

                sleep.run();

                System.out.printf("[%s] end\n", threadName);
            });
        }

        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    void 타임아웃_() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        int nThreads = 3;
        int numFinishedThreads = IntStream.range(0, nThreads).map(i -> {
            Future<Integer> task = executor.submit(() -> {
                sleep.run();
                return 1;
            });

            try {
                return task.get(SLEEPING_TIME_IN_MILLISECONDS / 10, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                System.out.println("failed (timeout)");
                e.printStackTrace();
            }

            return 0;
        }).sum();


        assertThat(numFinishedThreads < nThreads).isTrue();
    }
}
