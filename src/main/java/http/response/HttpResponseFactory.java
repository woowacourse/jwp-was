package http.response;

import http.HttpHeaders;
import http.HttpVersion;

public class HttpResponseFactory {
    public static HttpResponse makeResponse(HttpResponseEntity responseEntity) {
        HttpStatusLine statusLine =
                new HttpStatusLine(HttpVersion.DEFAULT_VERSION, responseEntity.getStatus());
        HttpHeaders httpHeaders = responseEntity.getHeaders();

        return new HttpResponse(statusLine, httpHeaders);
    }
}
