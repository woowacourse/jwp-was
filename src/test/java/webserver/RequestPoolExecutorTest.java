package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class RequestPoolExecutorTest {

    @DisplayName("요청이 들어온 순서대로 처리된다.")
    @Test
    void execute() throws InterruptedException {
        Queue<Integer> responseQueue = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(Integer.MAX_VALUE);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(16, 16, 1L, TimeUnit.SECONDS, queue);

        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 1; i <= 5; i++) {
            final int jobId = i;
            executor.execute(() -> {
                responseQueue.add(jobId);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();

        assertThat(responseQueue).containsExactly(1, 2, 3, 4, 5);
    }
}