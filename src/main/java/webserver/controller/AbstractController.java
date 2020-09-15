package webserver.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import webserver.domain.request.HttpRequest;
import webserver.domain.request.RequestMethod;
import webserver.domain.response.HttpResponse;

public abstract class AbstractController implements Controller {

    private final Map<RequestMethod, BiConsumer<HttpRequest, HttpResponse>> requestMethodMapper;

    public AbstractController() {
        this.requestMethodMapper = new HashMap<>();
        this.requestMethodMapper.put(RequestMethod.GET, this::doGet);
        this.requestMethodMapper.put(RequestMethod.POST, this::doPost);
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        requestMethodMapper.get(httpRequest.getMethod()).accept(httpRequest, httpResponse);
    }

    public abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse);

    public abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse);
}
