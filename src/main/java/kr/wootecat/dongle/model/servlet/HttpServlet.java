package kr.wootecat.dongle.model.servlet;

import static kr.wootecat.dongle.model.http.HttpMethod.*;

import kr.wootecat.dongle.model.http.exception.MethodNotAllowedException;
import kr.wootecat.dongle.model.http.request.HttpRequest;
import kr.wootecat.dongle.model.http.response.HttpResponse;

public abstract class HttpServlet extends GenericServlet {

    private final ServletMethodInvoker methodInvoker;

    public HttpServlet() {
        this(ServletMethodInvokerFactory.create());
    }

    private HttpServlet(ServletMethodInvoker methodInvoker) {
        this.methodInvoker = methodInvoker;
    }

    @Override
    public void doService(HttpRequest request, HttpResponse response) {
        methodInvoker.invoke(this, request, response);
    }

    protected void doGet(HttpRequest request, HttpResponse response) {
        throw new MethodNotAllowedException(GET);
    }

    protected void doPost(HttpRequest request, HttpResponse response) {
        throw new MethodNotAllowedException(POST);
    }
}
