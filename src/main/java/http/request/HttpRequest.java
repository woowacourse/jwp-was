package http.request;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestParams requestParams;

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestParams requestParams) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestParams = requestParams;
    }

    public boolean isMatchMethod(HttpMethod method) {
        return method.equals(requestLine.getMethod());
    }

    public boolean isNotFound() throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(getPath());
        return body.length == 0;
    }

    public boolean isNotLogined() {
        return !requestHeader.isLogined();
    }

    public boolean isMemberService() {
        return getPath().equals("/user/list.html");
    }

    public boolean isDynamicPage() {
        return getPath().equals("/user/list.html");
    }

    public String getHeader(String key) {
        return requestHeader.get(key);
    }

    public String getParameter(String key) {
        return requestParams.get(key);
    }

    public String getPath() {
        return requestLine.getPath();
    }
}
