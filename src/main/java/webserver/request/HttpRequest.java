package webserver.request;

import controller.exception.HttpRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.common.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    private HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequest of(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            RequestLine requestLine = RequestLine.of(br);
            RequestHeader requestHeader = RequestHeader.of(br);
            RequestBody requestBody = RequestBody.of(br, requestHeader.getContentLength());
            return new HttpRequest(requestLine, requestHeader, requestBody);
        } catch (IOException e) {
            log.debug("fail to input HttpRequest message", e);
        }
        throw new HttpRequestException(HttpStatus.BAD_REQUEST);
    }

    public boolean isGet() {
        return requestLine.isGet();
    }

    public boolean isPost() {
        return requestLine.isPost();
    }

    public boolean hasParameters() {
        return requestLine.hasParameters();
    }

    public String getParameter(String attributeName) {
        if (hasParameters()) {
            return requestLine.getParameterValue(attributeName);
        }
        return requestBody.getParameterValue(attributeName);
    }

    public String getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    public String getSource() {
        return requestLine.getPath();
    }

    public boolean containHeaderField(String headerField, String value) {
        return requestHeader.contains(headerField, value);
    }

    public String getHeaderFieldValue(String headerField) {
        return requestHeader.getHeaderFieldValue(headerField);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }
}
