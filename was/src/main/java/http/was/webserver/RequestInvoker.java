package http.was.webserver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import http.was.http.request.Request;
import http.was.http.response.Response;

public class RequestInvoker {

    private Object instance;
    private Method method;

    public RequestInvoker(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
    }

    public void invoke(Request request, Response response) throws InvocationTargetException, IllegalAccessException {
        method.invoke(instance, request, response);
    }
}
