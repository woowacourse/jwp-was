package web;

import web.servlet.Servlet;
import web.servlet.UserCreateServlet;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HandlerMapping {
    private static final Map<RequestMapping, Servlet> handlerMapping = new HashMap<>();

    static {
        handlerMapping.put(new RequestMapping("/user/create", HttpMethod.POST), new UserCreateServlet());
    }

    public static Servlet find(HttpRequest httpRequest) {
        RequestMapping requestMapping = new RequestMapping(httpRequest.getRequestPath(), httpRequest.getMethod());
        Servlet servlet = handlerMapping.get(requestMapping);
        if (Objects.isNull(servlet)) {
            throw new IllegalArgumentException(String.format("요청(%s: %s)을 처리할 수 없습니다.",
                    httpRequest.getMethod(), httpRequest.getRequestPath()));
        }
        return servlet;
    }
}
