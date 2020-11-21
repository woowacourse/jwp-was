package webserver.handleradaptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import webserver.controller.annotation.RequestMapping;
import webserver.messageconverter.HttpMessageConverter;
import webserver.request.ServletRequest;
import webserver.response.ModelAndView;

public class DefaultHandlerAdaptor implements HandlerAdaptor {

    public ModelAndView invoke(Method handler, ServletRequest servletRequest, HttpMessageConverter converter) {
        final boolean resource = handler.getAnnotation(RequestMapping.class).isResource();
        Class<?>[] parameterTypes = handler.getParameterTypes();

        try {
            Object handlers = handler.getDeclaringClass().getDeclaredConstructor().newInstance();
            if (parameterTypes.length == 0) {
                return (ModelAndView)handler.invoke(handlers);
            }
            if (resource) {
                return (ModelAndView)handler.invoke(handlers, servletRequest);
            }
            if (converter.isSupport(parameterTypes[0], servletRequest.getBody())) {
                return (ModelAndView)handler.invoke(handlers,
                    parameterTypes[0].cast(converter.convert(parameterTypes[0], servletRequest.getBody())));
            }
            return null;
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            throw new IllegalArgumentException();
        }
    }
}
