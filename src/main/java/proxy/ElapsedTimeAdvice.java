package proxy;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public class ElapsedTimeAdvice implements MethodInterceptor {

    private ElapsedTimeAdvice() {
    }

    public static Advice getInstance() {
        return BillPughSingleton.INSTANCE;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Logger logger = getLogger(invocation.getThis().getClass());

        StopWatch sw = new StopWatch();
        sw.start(invocation.getMethod().getName());

        Object ret = invokeUsingProceed(invocation);

        sw.stop();

        logger.debug("ElapsedTime: {} ms\n", sw.getLastTaskTimeMillis());

        return ret;
    }

    private Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    private Object invokeUsingProceed(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

    private static class BillPughSingleton {
        private static final ElapsedTimeAdvice INSTANCE = new ElapsedTimeAdvice();
    }
}


