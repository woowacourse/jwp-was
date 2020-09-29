package webserver;

import webserver.controller.ExceptionHandler;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.staticfile.StaticFileController;
import webserver.staticfile.StaticFileMatcher;
import webserver.user.UserController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {
    private static Map<String, Method> postMappings = new HashMap<>();

    static {
        try {
            postMappings.put("/user/create", UserController.class.getDeclaredMethod("doPost", HttpRequest.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static HttpResponse mapping(HttpRequest httpRequest) {
        if (StaticFileMatcher.isStaticFileResourcePath(httpRequest.getResourcePath())) {
            return StaticFileController.processStaticFile(httpRequest);
        }
        try {
            if (httpRequest.getHttpMethod().equals(HttpMethod.POST)) {
                Method method = postMappings.get(httpRequest.getResourcePath());
                return (HttpResponse) method.invoke(null, httpRequest);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            return ExceptionHandler.processException(e);
        }
        return ExceptionHandler.processException(new IllegalArgumentException("등록되지 않은 요청입니다."));
    }
}
