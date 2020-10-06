package webserver.http.message;

import webserver.http.body.DefaultHttpBody;
import webserver.http.body.HttpBody;
import webserver.http.header.HttpHeader;
import webserver.http.response.HttpStatus;
import webserver.http.response.StatusLine;

import java.util.Map;

public class HttpResponseMessage extends HttpMessage {
    private HttpResponseMessage(StatusLine statusLine, HttpHeader httpHeader, HttpBody httpBody) {
        super(statusLine, httpHeader, httpBody);
    }

    public static HttpResponseMessage of(HttpStatus httpStatus, Map<String, String> headers, String body) {
        StatusLine statusLine = new StatusLine(httpStatus);
        HttpHeader httpHeader = HttpHeader.from(headers);
        HttpBody httpBody = DefaultHttpBody.from(body);

        return new HttpResponseMessage(statusLine, httpHeader, httpBody);
    }

    public static HttpResponseMessage of(HttpStatus httpStatus, Map<String, String> headers) {
        StatusLine statusLine = new StatusLine(httpStatus);
        HttpHeader httpHeader = HttpHeader.from(headers);
        HttpBody httpBody = HttpBody.empty();

        return new HttpResponseMessage(statusLine, httpHeader, httpBody);
    }
}
