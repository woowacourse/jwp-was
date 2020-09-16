package webserver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

import model.User;

public class HandlerAdaptor {

    public ServletResponse invoke(Method handler, ServletRequest servletRequest,
        List<HttpMessageConverter> converters) {
        Class<?>[] parameterTypes = handler.getParameterTypes();
        User user = null;

        // for (HttpMessageConverter converter : converters) {
        //     for (Class<?> parameterType : handler.getParameterTypes()) {
        //         if (converter.isSupport(parameterType, servletRequest.getBody())) {
        //             Object parameter = converter.convert(parameterType, servletRequest.getBody());
        //             parameters.add(parameterType.cast(parameter));
        //             break;
        //         }
        //     }
        // }



        Object handlers = null;
        try {
            handlers = handler.getDeclaringClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            if(parameterTypes.length == 0) {
                return (ServletResponse)handler.invoke(handlers);
            }
            if(converters.get(0).isSupport(parameterTypes[0], servletRequest.getBody())){
                user = (User)converters.get(0).convert(parameterTypes[0], servletRequest.getBody());
            };
            return (ServletResponse)handler.invoke(handlers, user);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }
}
