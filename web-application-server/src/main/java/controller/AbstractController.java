package controller;

import java.util.Objects;

import exception.UnAuthenticationException;
import http.HttpSession;
import http.HttpStatus;
import http.HttpVersion;
import http.request.HttpRequest;
import http.response.HttpResponse;
import servlet.HttpServlet;

public abstract class AbstractController implements HttpServlet {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpVersion httpVersion = httpRequest.getVersion();
        if (!httpVersion.isValid()) {
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

    protected void validateLogin(HttpRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        String logined = String.valueOf(session.getAttribute("logined"));
        if (Objects.isNull(logined) || !"true".equals(logined)) {
            throw new UnAuthenticationException("로그인이 필요한 서비스입니다");
        }
    }
}
