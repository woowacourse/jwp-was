package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequestUtils;
import webserver.RequestHandler;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private RequestLine requestLine;
    private RequestHeader header;
    private RequestBody body;

    public HttpRequest(RequestLine requestLine, RequestHeader header, RequestBody body) {
        this.requestLine = requestLine;
        this.header = header;
        this.body = body;
    }

    public String getAbsPath() {
        return requestLine.getAbsPath();
    }

    public String getFilePath() {
        if (header.getHeader("Accept").contains("text/html")) {
            return HttpRequestUtils.generateTemplateFilePath(requestLine.getAbsPath());
        }
        return HttpRequestUtils.generateStaticFilePath(requestLine.getAbsPath());
    }

    public String getParam(String key) {
        return requestLine.getQueryString(key);
    }

    public RequestMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getBody(String key) {
        return body.getBody(key);
    }

    public String getHeader(String key) {
        return header.getHeader(key);
    }
}
