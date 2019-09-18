package http.request;

import http.common.HttpBody;
import http.common.HttpHeader;
import http.common.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private RequestStartLine requestStartLine;
    private HttpHeader httpHeader;
    private HttpBody httpBody;

    public HttpRequest(BufferedReader br) {
        try {
            requestStartLine = RequestStartLine.of(br);
            httpHeader = HttpHeader.of(br);
            httpBody = HttpBody.of(br, httpHeader);
        } catch (IOException e) {
            logger.debug("error : {}", e.getMessage());
        }
    }

    public String getUrl() {
        String path = requestStartLine.getPath();
        String protocol = requestStartLine.getProtocol();
        String host = httpHeader.getHeader("Host");

        return String.format("%s://%s%s", protocol, host, path);
    }

    public String getQuery(String key) {
        return requestStartLine.getQuery().get(key);
    }

    public String getPath() {
        return requestStartLine.getPath();
    }

    public HttpMethod getMethod() {
        return requestStartLine.getMethod();
    }

    public String getHeader(String headerKey) {
        return httpHeader.getHeader(headerKey);
    }

    public String getEntityValue(String entityKey) {
        return httpBody.getEntityValue(entityKey);
    }
}
