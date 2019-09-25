package proxy;

import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import static org.assertj.core.api.Assertions.assertThat;

class AccessLoggingAdviceTest {

    @Test
    void 싱글톤_객체인지_확인() {
        assertThat(AccessLoggingAdvice.getInstance() == AccessLoggingAdvice.getInstance()).isTrue();
    }

    @Test
    void begin_end_출력확인() {
        Worker worker = new SimpleWorker();

        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(worker);
        pf.addAdvice(AccessLoggingAdvice.getInstance());

        Worker proxiedWorker = (Worker) pf.getProxy();

        proxiedWorker.work1();
        proxiedWorker.work2();
    }

    private interface Worker {
        void work1();
        void work2();
    }

    private class SimpleWorker implements Worker {

        @Override
        public void work1() {
            System.out.println("work1() called");
        }

        @Override
        public void work2() {
            System.out.println("workr2() called");
        }
    }
}