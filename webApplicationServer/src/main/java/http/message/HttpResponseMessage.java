package http.message;

import http.body.DefaultHttpBody;
import http.body.HttpBody;
import http.header.HttpHeaders;
import http.response.HttpStatus;
import http.response.StatusLine;

public class HttpResponseMessage extends HttpMessage {
    private HttpResponseMessage(StatusLine statusLine, HttpHeaders httpHeaders, HttpBody httpBody) {
        super(statusLine, httpHeaders, httpBody);
    }

    public static HttpResponseMessage of(HttpStatus httpStatus, HttpHeaders httpHeaders, String body) {
        StatusLine statusLine = new StatusLine(httpStatus);
        HttpBody httpBody = DefaultHttpBody.from(body);

        return new HttpResponseMessage(statusLine, httpHeaders, httpBody);
    }

    public static HttpResponseMessage of(HttpStatus httpStatus, HttpHeaders httpHeaders) {
        StatusLine statusLine = new StatusLine(httpStatus);
        HttpBody httpBody = HttpBody.empty();

        return new HttpResponseMessage(statusLine, httpHeaders, httpBody);
    }
}
