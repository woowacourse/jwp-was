package webserver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import model.User;

public class DefaultHandlerAdaptor implements HandlerAdaptor{

    public ServletResponse invoke(Method handler, ServletRequest servletRequest,
        HttpMessageConverter converter) {

        Class<?>[] parameterTypes = handler.getParameterTypes();
        User user = null;

        try {
            Object handlers = handler.getDeclaringClass().newInstance();
            if (parameterTypes.length == 0) {
                return (ServletResponse)handler.invoke(handlers);
            }
            if (converter.isSupport(parameterTypes[0], servletRequest.getBody())) {
                user = (User)converter.convert(parameterTypes[0], servletRequest.getBody());
            }
            return (ServletResponse)handler.invoke(handlers, user);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new IllegalArgumentException();
        }
    }
}
