package controller;

import java.util.Objects;

import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import servlet.HttpServlet;

public abstract class AbstractController implements HttpServlet {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        String version = httpRequest.getVersion();
        if (Objects.isNull(version) || version.isEmpty() || version.endsWith("0.9") || version.endsWith("1.0")) {
            httpResponse.setStatus(HttpStatus.BAD_REQUEST);
            return;
        }
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
                httpResponse.methodNotAllowed();
        }
    }

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.methodNotAllowed();
    }

    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.methodNotAllowed();
    }

    protected void doDelete(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.methodNotAllowed();
    }

    protected void doPut(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.methodNotAllowed();
    }
}
