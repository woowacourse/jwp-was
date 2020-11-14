package webserver.http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.Body;
import webserver.http.HttpHeaders;
import webserver.http.Url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequest.class);

    private HttpRequestStartLine httpRequestStartLine;
    private HttpHeaders httpHeaders;
    private Body body;

    public HttpRequest(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String startLine = bufferedReader.readLine();
        httpRequestStartLine = HttpRequestStartLine.of(startLine);
        httpHeaders = HttpHeaders.of(bufferedReader);
        LOGGER.info("Create Clear!");
    }

    public Url getUrl() {
        return httpRequestStartLine.getUrl();
    }

    public boolean isGetRequest() {
        return httpRequestStartLine.isGetRequest();
    }

    public boolean isPostRequest() {
        return httpRequestStartLine.isPostRequest();
    }
}
