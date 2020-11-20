package domain.controller;

import domain.request.HttpRequest;
import domain.request.RequestMethod;
import domain.response.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

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

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.respondMethodNotAllowed();
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.respondMethodNotAllowed();
    }
}
