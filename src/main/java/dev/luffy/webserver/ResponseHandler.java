package dev.luffy.webserver;

import dev.luffy.http.RequestMapper;
import dev.luffy.http.request.HttpRequest;
import dev.luffy.http.response.HttpResponse;
import dev.luffy.http.session.HttpSessionStorage;
import dev.luffy.http.session.SessionIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ResponseHandler {

    private static final String SET_COOKIE = "Set-Cookie";
    private static final String SESSION_ID_FORMAT = "sessionId=%s;";
    private static final String SESSION_ID = "sessionId";
    private static final String EMPTY_STRING = "";

    private static final Logger log = LoggerFactory.getLogger(ResponseHandler.class);

    public static void run(HttpRequest request, HttpResponse response) {
        if (request.isStaticRequest()) {
            response.ok(request);
            return;
        }

        Method controllerMethod = RequestMapper.get(request.getPath());

        if (controllerMethod == null) {
            response.notFound(request);
            return;
        }

        response.addHeader(SET_COOKIE, String.format(SESSION_ID_FORMAT, getSessionId(request)));

        try {
            controllerMethod.invoke(null, request, response);

        } catch (InvocationTargetException | IllegalAccessException e) {
            log.error("error : {}", e.getMessage());
        }
    }

    private static String getSessionId(HttpRequest request) {
        String sessionId = request.getCookie(SESSION_ID);
        HttpSessionStorage httpSessionStorage = HttpSessionStorage.getInstance();

        if (EMPTY_STRING.equals(sessionId)) {
            sessionId = new SessionIdGenerator().generate();
            httpSessionStorage.createSession(sessionId);
        }

        return sessionId;
    }
}
