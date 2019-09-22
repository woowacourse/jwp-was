package controller;

import http.NotSupportedHttpMethodException;
import http.request.HttpMethodType;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class BasicController implements Controller {

    @Override
    public HttpResponse service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        if (HttpMethodType.GET.equals(request.getHttpMethod())) {
            return doGet(request, response);
        }
        if (HttpMethodType.POST.equals(request.getHttpMethod())) {
            return doPost(request, response);
        }
        throw new NotSupportedHttpMethodException();
    }

    public abstract HttpResponse doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;

    public abstract HttpResponse doPost(HttpRequest request, HttpResponse response) throws IOException;
}
