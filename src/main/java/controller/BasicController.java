package controller;

import http.NotSupportedHttpMethodException;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class BasicController implements Controller {

    @Override
    public HttpResponse service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        if(HttpMethod.GET.equals(request.getHttpMethod())) {
            return doGet(request, response);
        }
        if(HttpMethod.POST.equals(request.getHttpMethod())) {
            return doPost(request, response);
        }
        throw new NotSupportedHttpMethodException();
    }

    public abstract HttpResponse doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;

    public abstract HttpResponse doPost(HttpRequest request, HttpResponse response) throws IOException;
}
