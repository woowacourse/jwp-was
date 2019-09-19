package http;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {

    }

    public void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {

    }

    public void doPost(HttpRequest request, HttpResponse response) {

    }
}
