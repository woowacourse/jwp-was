package webserver.http.request;

import webserver.http.Body;
import webserver.http.HttpHeaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private HttpRequestStartLine httpRequestStartLine;
    private HttpHeaders httpHeaders;
    private Body body;

    public HttpRequest(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String startLine = bufferedReader.readLine();
        httpRequestStartLine = HttpRequestStartLine.of(startLine);
        httpHeaders = HttpHeaders.of(bufferedReader);
        body = Body.of(bufferedReader);
    }
}
