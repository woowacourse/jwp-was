package http.message;

import http.body.HttpBody;
import http.header.HttpHeaders;
import http.request.HttpUri;
import http.request.RequestLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public class HttpRequestMessage extends HttpMessage {
    private final RequestLine requestLine;

    public HttpRequestMessage(RequestLine requestLine, HttpHeaders httpHeaders, HttpBody httpBody) {
        super(requestLine, httpHeaders, httpBody);
        this.requestLine = requestLine;
    }

    public static HttpRequestMessage from(BufferedReader br) throws IOException {
        RequestLine requestLine = RequestLine.from(br.readLine());
        HttpHeaders httpHeaders = HttpHeaders.from(br);
        HttpBody httpBody = httpHeaders.createHttpBody(br);

        return new HttpRequestMessage(requestLine, httpHeaders, httpBody);
    }

    public HttpUri getHttpUri() {
        return this.requestLine.getHttpUri();
    }

    public Optional<String> getHeaderValue(String headerField) {
        return super.httpHeaders.getHeaderValue(headerField);
    }
}
