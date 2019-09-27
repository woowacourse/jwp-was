package webserver.servlet;

import exceptions.MethodNotAllowedException;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestMethod;
import webserver.http.request.RequestUri;
import webserver.http.response.HttpResponse;
import webserver.view.ModelAndView;
import webserver.view.View;

import java.io.IOException;

public abstract class RequestServlet implements HttpServlet {
    public static final String METHOD_NOT_ALLOW_MESSAGE = "지원하지 않는 메소드 입니다.";

    @Override
    public boolean canMapping(RequestUri requestUri) {
        return requestUri.isSameAbsPath(getUrl());
    }

    @Override
    public ModelAndView run(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (httpRequest.getMethod() == RequestMethod.GET) {
            return doGet(httpRequest, httpResponse);
        }

        if (httpRequest.getMethod() == RequestMethod.POST) {
            return doPost(httpRequest, httpResponse);
        }
        throw new MethodNotAllowedException(httpRequest.getMethod());
    }

    public ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        throw new MethodNotAllowedException(httpRequest.getMethod());
    }

    public ModelAndView doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        throw new MethodNotAllowedException(httpRequest.getMethod());
    }

    protected abstract String getUrl();

}
