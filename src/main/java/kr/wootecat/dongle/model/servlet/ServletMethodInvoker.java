package kr.wootecat.dongle.model.servlet;

import java.util.Map;

import kr.wootecat.dongle.model.http.HttpMethod;
import kr.wootecat.dongle.model.http.request.HttpRequest;
import kr.wootecat.dongle.model.http.response.HttpResponse;

public class ServletMethodInvoker {

    private final Map<HttpMethod, TriConsumer<HttpServlet, HttpRequest, HttpResponse>> methodInvoker;

    public ServletMethodInvoker(
            Map<HttpMethod, TriConsumer<HttpServlet, HttpRequest, HttpResponse>> methodInvoker) {
        this.methodInvoker = methodInvoker;
    }

    public void invoke(HttpServlet servlet, HttpRequest request, HttpResponse response) {
        TriConsumer<HttpServlet, HttpRequest, HttpResponse> httpServiceInvoker = methodInvoker.get(request.getMethod());
        httpServiceInvoker.accept(servlet, request, response);
    }

    interface TriConsumer<T, R, V> {
        void accept(T t, R r, V v);
    }
}
