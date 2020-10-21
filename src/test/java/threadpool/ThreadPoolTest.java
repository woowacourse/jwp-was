package threadpool;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreadPoolTest {

    @DisplayName("ThreadPoolExecutor는 생성 시 지정된 개수의 쓰레드를 생성한다.")
    @Test
    void createThreadTest() {
        int nThreads = 10;
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(nThreads, nThreads,
                        2L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        assertThat(threadPoolExecutor.getCorePoolSize()).isEqualTo(nThreads);
        assertThat(threadPoolExecutor.getMaximumPoolSize()).isEqualTo(nThreads);
    }

    @DisplayName("가능한 Thread 개수를 초과하면 Queue에서 대기한다.")
    @Test
    void waitingQueueTest() {
        int nThreads = 1;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        Runnable runnable1 = () -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertThat(threadPoolExecutor.getQueue().size()).isGreaterThan(1);
        };
        Runnable runnable2 = () -> {
        };

        threadPoolExecutor.execute(runnable1);
        threadPoolExecutor.execute(runnable2);
        threadPoolExecutor.execute(runnable2);
    }

    @DisplayName("지정된 Thread 개수를 초과해도 작업을 모두 처리한다.")
    @Test
    void overThreadTest() throws InterruptedException {
        int nThreads = 1;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        CountDownLatch countDownLatch = new CountDownLatch(3);
        Runnable runnable = countDownLatch::countDown;

        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);

        countDownLatch.await();
    }
}
