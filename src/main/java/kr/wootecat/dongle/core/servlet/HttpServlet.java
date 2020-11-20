package kr.wootecat.dongle.core.servlet;

import static kr.wootecat.dongle.http.HttpMethod.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import kr.wootecat.dongle.http.HttpMethod;
import kr.wootecat.dongle.http.exception.MethodNotAllowedException;
import kr.wootecat.dongle.http.request.HttpRequest;
import kr.wootecat.dongle.http.response.HttpResponse;

public abstract class HttpServlet extends GenericServlet {

    private final Map<HttpMethod, BiConsumer<HttpRequest, HttpResponse>> methodActionPair = new HashMap<>();

    public HttpServlet() {
        this.methodActionPair.put(GET, this::doGet);
        this.methodActionPair.put(POST, this::doPost);
    }

    @Override
    public void doService(HttpRequest request, HttpResponse response) {
        BiConsumer<HttpRequest, HttpResponse> httpServiceInvoker = methodActionPair.get(request.getMethod());
        httpServiceInvoker.accept(request, response);
    }

    protected void doGet(HttpRequest request, HttpResponse response) {
        throw new MethodNotAllowedException(GET);
    }

    protected void doPost(HttpRequest request, HttpResponse response) {
        throw new MethodNotAllowedException(POST);
    }
}
