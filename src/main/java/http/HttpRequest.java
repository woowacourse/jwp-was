package http;

import java.io.BufferedReader;

public class HttpRequest {
    private final RequestLine requestLine;
    private final BufferedReader bufferedReader;

    public HttpRequest(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
        this.requestLine = new RequestLine(bufferedReader);
    }

}
