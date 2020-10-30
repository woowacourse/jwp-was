package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import type.method.MethodType;
import exception.NotSupportedMethodException;
import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
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

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        throw new NotSupportedMethodException("지원하지 않는 Method 입니다. Method: GET");
    }

    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        throw new NotSupportedMethodException("지원하지 않는 Method 입니다. Method: POST");
    }
}
