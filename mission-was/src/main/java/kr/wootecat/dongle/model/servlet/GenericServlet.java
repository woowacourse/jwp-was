package kr.wootecat.dongle.model.servlet;

import kr.wootecat.dongle.model.http.request.HttpRequest;
import kr.wootecat.dongle.model.http.response.HttpResponse;

public abstract class GenericServlet {

    public abstract void doService(HttpRequest request, HttpResponse response);
}
