package webserver.http.message;

import webserver.http.body.HttpBody;
import webserver.http.header.HttpHeader;
import webserver.http.request.HttpUri;
import webserver.http.request.RequestLine;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestMessage extends HttpMessage {
    private final RequestLine requestLine;

    public HttpRequestMessage(RequestLine requestLine, HttpHeader httpHeader, HttpBody httpBody) {
        super(requestLine, httpHeader, httpBody);
        this.requestLine = requestLine;
    }

    public static HttpRequestMessage from(BufferedReader br) throws IOException {
        RequestLine requestLine = RequestLine.from(br.readLine());
        HttpHeader httpHeader = HttpHeader.from(br);
        HttpBody httpBody = httpHeader.createHttpBody(br);

        return new HttpRequestMessage(requestLine, httpHeader, httpBody);
    }

    public HttpUri getHttpUri() {
        return this.requestLine.getHttpUri();
    }

    public String getHeaderValue(String headerField) {
        return super.httpHeader.getHeaderValue(headerField);
    }
}
