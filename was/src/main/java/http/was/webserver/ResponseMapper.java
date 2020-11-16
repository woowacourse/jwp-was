package http.was.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

import http.was.controller.MappedRequest;
import http.was.http.ContentType;
import http.was.http.request.Request;
import http.was.http.response.Response;

public class ResponseMapper {

    public static void responseMapping(Request request, OutputStream out, RequestMapper requestMapper) throws
            IOException,
            URISyntaxException,
            InvocationTargetException,
            IllegalAccessException {
        Response response = new Response(out);
        if (ContentType.isStaticFile(request.getPath())) {
            response.ok(request.getPath(), request.getContentType());
            return;
        }
        MappedRequest mappedRequest = new MappedRequest(request.getRequestMethod(), request.getPath());
        Method method = requestMapper.get(mappedRequest);
        Object instance = requestMapper.getInstance(method.getDeclaringClass());
        method.invoke(instance, request, response);
    }
}
