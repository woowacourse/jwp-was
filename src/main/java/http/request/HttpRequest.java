package http.request;

import java.io.BufferedReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.Headers;
import utils.IOUtils;

public class HttpRequest {
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final RequestLine requestLine;
    private final Headers requestHeaders;
    private final String body;

    private HttpRequest(RequestLine requestLine, Headers requestHeaders, String body) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.body = body;
    }

    public static HttpRequest from(BufferedReader bufferedReader) throws IOException {
        String firstLine = bufferedReader.readLine();
        logger.debug(firstLine);
        RequestLine requestLine = RequestLine.from(firstLine);
        Headers requestHeaders = Headers.from(IOUtils.readHeaders(bufferedReader));

        String body = null;
        if (requestHeaders.contains(CONTENT_LENGTH)) {
            body = IOUtils.readData(bufferedReader,
                    Integer.parseInt(requestHeaders.getAttribute(CONTENT_LENGTH)));
        }

        return new HttpRequest(requestLine, requestHeaders, body);
    }

    public String getAttribute(String attribute) {
        return requestHeaders.getAttribute(attribute);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getBody() {
        return body;
    }

    public boolean isPost() {
        return requestLine.isPost();
    }
}
