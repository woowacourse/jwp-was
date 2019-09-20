package http.response;

import http.HttpHeaders;
import http.HttpVersion;

import static http.HttpVersion.HTTP_1_1;
import static http.response.HttpStatus.FOUND;
import static http.response.HttpStatus.OK;

public class HttpResponseFactory {
    private static final HttpVersion DEFAULT_VERSION = HTTP_1_1;

    public static HttpResponse makeHttp200Response(HttpResponseBody responseBody) {
        HttpStatusLine statusLine = new HttpStatusLine(DEFAULT_VERSION, OK);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Content-Type", responseBody.getContentsType());
        headers.put("Content-Length", Integer.toString(responseBody.getBodyLength()));
        return new HttpResponse(statusLine, headers, responseBody.getBody());
    }

    public static HttpResponse makeHttp302Response(String location) {
        HttpStatusLine statusLine = new HttpStatusLine(DEFAULT_VERSION, FOUND);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Location", location);
        return new HttpResponse(statusLine, headers, null);
    }
}
