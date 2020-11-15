package kr.wootecat.dongle.core.servlet;

import kr.wootecat.dongle.http.request.HttpRequest;
import kr.wootecat.dongle.http.response.HttpResponse;

public abstract class GenericServlet {

    public abstract void doService(HttpRequest request, HttpResponse response);
}
