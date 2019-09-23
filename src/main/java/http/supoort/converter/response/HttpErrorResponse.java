package http.supoort.converter.response;

import http.model.common.HttpHeaders;
import http.model.common.HttpProtocols;
import http.model.response.HttpStatus;

public class HttpErrorResponse {
    public static HttpResponse generate(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.addHeader("Content-Type", "text/plain");
        return new HttpResponse(HttpProtocols.HTTP1, HttpStatus.OK, headers, message.getBytes());
    }
}
