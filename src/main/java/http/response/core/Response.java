package http.response.core;

import http.request.HttpRequest;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class Response {
    protected HttpRequest httpRequest;

    public Response(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public abstract ResponseBody doResponse() throws IOException, URISyntaxException;
}
