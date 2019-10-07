package proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccessLoggingAdvice implements MethodInterceptor {
    public static final String BEGIN_MESSAGE = "begin";
    public static final String END_MESSAGE = "end";

    private AccessLoggingAdvice() {
    }

    public static AccessLoggingAdvice getInstance() {
        return BillPughSingleton.INSTANCE;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?> clazz = invocation.getThis().getClass();

        Logger log = LoggerFactory.getLogger(clazz);

        log.debug(BEGIN_MESSAGE);

        Object ret = invocation.proceed();

        log.debug(END_MESSAGE);

        return ret;
    }

    private static class BillPughSingleton {
        private static final AccessLoggingAdvice INSTANCE = new AccessLoggingAdvice();
    }
}
