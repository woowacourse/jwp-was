package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.request.RequestMethod;
import webserver.http.response.HttpResponse;
import webserver.servlet.exception.MethodNotAllowedException;
import webserver.view.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractRequestServlet implements HttpServlet {
    public static final String METHOD_NOT_ALLOW_MESSAGE = "지원하지 않는 메소드 입니다.";

    @Override
    public boolean canMapping(HttpRequest httpRequest) {
        return httpRequest.getUri().isSameAbsPath(getUrl());
    }

    @Override
    public ModelAndView run(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (httpRequest.getMethod() == RequestMethod.GET) {
            return doGet(httpRequest, httpResponse);
        }

        if (httpRequest.getMethod() == RequestMethod.POST) {
            return doPost(httpRequest, httpResponse);
        }
        throw new MethodNotAllowedException(httpRequest.getMethod());
    }

    protected ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        throw new MethodNotAllowedException(httpRequest.getMethod());
    }

    protected ModelAndView doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        throw new MethodNotAllowedException(httpRequest.getMethod());
    }


    protected abstract String getUrl();

}
