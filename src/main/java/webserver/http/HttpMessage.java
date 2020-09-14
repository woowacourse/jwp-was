package webserver.http;

import exception.InvalidHttpMessageException;
import webserver.http.body.HttpBody;
import webserver.http.header.HttpHeader;
import webserver.http.request.RequestLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class HttpMessage {
    private final RequestLine requestLine;
    private final HttpHeader httpHeader;
    private final HttpBody httpBody;

    public HttpMessage(RequestLine requestLine, HttpHeader httpHeader, HttpBody httpBody) {
        this.requestLine = requestLine;
        this.httpHeader = httpHeader;
        this.httpBody = httpBody;
    }

    public static HttpMessage from(BufferedReader br) throws IOException {
        RequestLine requestLine = RequestLine.from(br.readLine());
        HttpHeader httpHeader = createHttpHeader(br);
        HttpBody httpBody = httpHeader.createHttpBody(br);

        return new HttpMessage(requestLine, httpHeader, httpBody);
    }

    private static HttpHeader createHttpHeader(BufferedReader br) throws IOException {
        HttpHeader.Builder builder = new HttpHeader.Builder();

        while (true) {
            String headerLine = br.readLine();

            if (Objects.isNull(headerLine)) {
                throw new InvalidHttpMessageException("null");
            }

            if (headerLine.isEmpty()) {
                break;
            }

            builder.addHeaderLine(headerLine);
        }

        return builder.build();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public HttpBody getHttpBody() {
        return httpBody;
    }
}
