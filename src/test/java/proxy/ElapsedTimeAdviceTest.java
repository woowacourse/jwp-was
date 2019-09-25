package proxy;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;

class ElapsedTimeAdviceTest {
    private static final Logger log = LoggerFactory.getLogger(ElapsedTimeAdviceTest.class);

    @Test
    void ElapsedTimeAdvice_() throws InterruptedException {
        Worker slowPrinter = new SlowPrinter();

        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(slowPrinter);
        pf.addAdvice(ElapsedTimeAdvice.getInstance());

        Worker proxiedSlowPrinter = (Worker) pf.getProxy();

        proxiedSlowPrinter.print(5);
    }

    interface Worker {
        void work();
        int print(int number) throws InterruptedException;
    }

    class SlowPrinter implements Worker {
        private final long SLEEP_PERIOD = 100l;

        @Override
        public void work() {
            log.debug("work() called");
        }

        @Override
        public int print(int numOfPrint) throws InterruptedException {
            log.debug("print() called");
            for (int i = 0; i < numOfPrint; i++) {
                Thread.sleep(SLEEP_PERIOD);
                System.out.print("");
            }

            return numOfPrint;
        }
    }
}

