package kr.wootecat.dongle.application.servlet;

import kr.wootecat.dongle.application.http.request.HttpRequest;
import kr.wootecat.dongle.application.http.response.HttpResponse;

public abstract class GenericServlet {

    public abstract void doService(HttpRequest request, HttpResponse response);
}
