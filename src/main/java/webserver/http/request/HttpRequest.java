package webserver.http.request;

import webserver.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private final HttpRequestStartLine httpRequestStartLine;
    private final HttpHeaders httpHeaders;
    private final Body body;

    public HttpRequest(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String startLine = bufferedReader.readLine();
        httpRequestStartLine = HttpRequestStartLine.of(startLine);
        httpHeaders = HttpHeaders.of(bufferedReader);
        HttpHeader contentLengthHeader = HttpHeader.of(HttpHeaderType.CONTENT_LENGTH);
        body = initializeBody(bufferedReader, contentLengthHeader);
    }

    private Body initializeBody(BufferedReader bufferedReader, HttpHeader contentLengthHeader) throws IOException {
        if (httpHeaders.contains(contentLengthHeader)) {
            String contentLength = httpHeaders.getHttpHeader(contentLengthHeader);
            return Body.of(bufferedReader, contentLength);
        }
        return Body.emptyBody();
    }

    public boolean isGetRequest() {
        return httpRequestStartLine.isGetRequest();
    }

    public boolean isPostRequest() {
        return httpRequestStartLine.isPostRequest();
    }

    public URL getUrl() {
        return httpRequestStartLine.getUrl();
    }

    public Parameters getParameters() {
        return body.getParameters();
    }
}
