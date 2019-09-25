package was.controller;

import was.exception.MethodNotAllowedException;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController implements Controller {
    private final Map<HttpMethod, Controller> map = new HashMap<>();

    {
        map.put(HttpMethod.GET, this::doGet);
        map.put(HttpMethod.POST, this::doPost);
    }

    @Override
    public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        HttpMethod httpMethod = httpRequest.getRequestLine().getHttpMethod();
        map.get(httpMethod).service(httpRequest, httpResponse);
    }

    void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        throw new MethodNotAllowedException();
    }

    void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        throw new MethodNotAllowedException();
    }
}
