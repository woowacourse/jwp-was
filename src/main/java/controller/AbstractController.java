package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractController implements Controller {

    private static final String GET = "GET";
    private static final String POST = "POST";

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (GET.equals(httpRequest.getMethod())) {
            doGet(httpRequest, httpResponse);
            return ;
        }
        if (POST.equals(httpRequest.getMethod())) {
            doPost(httpRequest, httpResponse);
            return ;
        }
        throw new RuntimeException();
    }

    abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException;

    abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
