package webserver.handleradaptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import model.User;
import webserver.messageconverter.HttpMessageConverter;
import webserver.response.ModelAndView;
import webserver.request.ServletRequest;
import webserver.controller.annotation.RequestMapping;

public class DefaultHandlerAdaptor implements HandlerAdaptor{

    public ModelAndView invoke(Method handler, ServletRequest servletRequest, HttpMessageConverter converter) {
        final boolean resource = handler.getAnnotation(RequestMapping.class).isResource();
        Class<?>[] parameterTypes = handler.getParameterTypes();
        User user = null;

        try {
            Object handlers = handler.getDeclaringClass().getDeclaredConstructor().newInstance();
            if (parameterTypes.length == 0) {
                final ModelAndView mav = (ModelAndView)handler.invoke(handlers);
                mav.setHttpProtocolVersion(servletRequest.getProtocolVersion());
                return mav;
            }
            if (converter.isSupport(parameterTypes[0], servletRequest.getBody())) {
                user = (User)converter.convert(parameterTypes[0], servletRequest.getBody());
            }
            if (resource) {
                return (ModelAndView)handler.invoke(handlers, servletRequest);
            }
            final ModelAndView mav = (ModelAndView)handler.invoke(handlers, user);
            mav.setHttpProtocolVersion(servletRequest.getProtocolVersion());
            return mav;
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            throw new IllegalArgumentException();
        }
    }
}
