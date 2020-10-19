package webserver.http.message;

import webserver.http.body.DefaultHttpBody;
import webserver.http.body.HttpBody;
import webserver.http.header.HttpHeaders;
import webserver.http.response.HttpStatus;
import webserver.http.response.StatusLine;

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
