package controller;

import controller.exception.NotFoundUrlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.ModelAndView;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ControllerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    public static ModelAndView executeMethod(HttpRequest httpRequest, HttpResponse httpResponse, Method method) {
        try {
            Controller clazz = (Controller) method.getDeclaringClass().getConstructor().newInstance();
            return (ModelAndView) method.invoke(clazz, httpRequest, httpResponse);
        } catch (IllegalAccessException | InvocationTargetException
                | NoSuchMethodException | InstantiationException e) {
            logger.error(e.getMessage());
            throw new NotFoundUrlException(e);
        }
    }
}
