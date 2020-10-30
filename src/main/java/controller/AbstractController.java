package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import http.HttpSessionStorage;
import type.method.MethodType;
import exception.NotSupportedMethodException;
import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractController implements Controller {

    protected HttpSessionStorage httpSessionStorage = HttpSessionStorage.getInstance();

    @Override
    public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (MethodType.GET == httpRequest.getMethod()) {
            doGet(httpRequest, httpResponse);
            return ;
        }
        if (MethodType.POST == httpRequest.getMethod()) {
            doPost(httpRequest, httpResponse);
            return ;
        }
        throw new NotSupportedMethodException("지원하지 않는 Method 입니다. Method: " + httpRequest.getMethod());
    }

    protected void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException, URISyntaxException {
        throw new NotSupportedMethodException("지원하지 않는 Method 입니다. Method: GET");
    }

    protected void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        throw new NotSupportedMethodException("지원하지 않는 Method 입니다. Method: POST");
    }
}
