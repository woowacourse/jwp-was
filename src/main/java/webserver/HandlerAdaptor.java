package webserver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerAdaptor {

    public ServletResponse invoke(Method handler, ServletRequest servletRequest,
        HttpMessageConverter messageConverter) {

        Class<?>[] parameterTypes = handler.getParameterTypes();
        Object converted = messageConverter.convert(parameterTypes[0], servletRequest.getBody());

        try {
            return (ServletResponse)handler.invoke(HandlerAdaptor.class, converted);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }
}
