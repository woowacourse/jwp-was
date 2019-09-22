package http.supoort;

import http.model.HttpHeaders;
import http.model.HttpProtocols;
import http.model.HttpResponse;
import http.model.HttpStatus;

public class HttpErrorResponse {
    public static HttpResponse generate(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.addHeader("Content-Type", "text/plain");
        return new HttpResponse(HttpProtocols.HTTP1, HttpStatus.OK, headers, message.getBytes());
    }
}
