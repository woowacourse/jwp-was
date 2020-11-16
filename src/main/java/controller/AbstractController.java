package controller;

import http.HttpRequest;
import http.HttpResponse;

public abstract class AbstractController implements HttpServlet {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        switch (httpRequest.getMethod()) {
            case GET:
                doGet(httpRequest, httpResponse);
                break;
            case POST:
                doPost(httpRequest, httpResponse);
                break;
            case DELETE:
                doDelete(httpRequest, httpResponse);
                break;
            case PUT:
                doPut(httpRequest, httpResponse);
                break;
            default:
                throw new IllegalArgumentException("잘못된 요청입니다.");
        }
    }

    protected abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse);

    protected abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse);

    protected abstract void doDelete(HttpRequest httpRequest, HttpResponse httpResponse);

    protected abstract void doPut(HttpRequest httpRequest, HttpResponse httpResponse);
}
