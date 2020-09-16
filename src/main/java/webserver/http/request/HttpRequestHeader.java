package webserver.http.request;

import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.io.InputStream;

public class HttpRequestHeader {
    private final HttpRequests httpRequests;

    public HttpRequestHeader(InputStream inputStream) throws IOException {
        this.httpRequests = new HttpRequests(inputStream);
    }

    public HttpResponse getHttpResponse() {
        String httpRequestMethod = httpRequests.getMethodType();
        HttpResponse httpResponse = HttpRequestMethod.getHttpResponse(httpRequestMethod);
        httpResponse.initHttpRequests(httpRequests);
        return httpResponse;
    }
}
