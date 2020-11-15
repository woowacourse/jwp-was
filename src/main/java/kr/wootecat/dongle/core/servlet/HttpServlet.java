package kr.wootecat.dongle.core.servlet;

import static kr.wootecat.dongle.http.HttpMethod.*;

import kr.wootecat.dongle.http.exception.MethodNotAllowedException;
import kr.wootecat.dongle.http.request.HttpRequest;
import kr.wootecat.dongle.http.response.HttpResponse;

public abstract class HttpServlet extends GenericServlet {

    @Override
    public void doService(HttpRequest request, HttpResponse response) {
        if (request.isGetMethod()) {
            this.doGet(request, response);
            return;
        }
        this.doPost(request, response);
    }

    protected void doGet(HttpRequest request, HttpResponse response) {
        throw new MethodNotAllowedException(GET);
    }

    protected void doPost(HttpRequest request, HttpResponse response) {
        throw new MethodNotAllowedException(POST);
    }
}
