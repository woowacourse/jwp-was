package was.http.servlet;

import was.http.request.HttpRequest;
import was.http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractServlet implements Servlet {
    private final Map<String, Servlet> MAP = new HashMap<>();

    {
        MAP.put("GET", this::doGet);
        MAP.put("POST", this::doPost);
    }

    public HttpResponse service(final HttpRequest request) {
        String method = request.getMethod();
        return MAP.get(method).service(request);
    }

    protected HttpResponse doPost(final HttpRequest request) {
        // TODO: 기본으로 METHOD_NOT_ALLOWED 에러 리턴하도록
        return null;
    }
    protected HttpResponse doGet(final HttpRequest request) {
        // TODO: 기본으로 METHOD_NOT_ALLOWED 에러 리턴하도록
        return null;
    }
}
