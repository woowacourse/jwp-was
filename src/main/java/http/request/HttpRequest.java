package http.request;

import http.session.HttpSession;
import http.session.HttpSessionStorage;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private static final String SESSIONID = "SESSIONID";
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public HttpRequest(BufferedReader br) throws IOException {
        this.requestLine = new RequestLine(br);
        this.requestHeader = new RequestHeader(br);
        this.requestBody = new RequestBody(br, this.requestHeader.getContentLength(), this.requestHeader.getContentType());
    }

    public HttpSession retrieveHttpSession() {
        if (this.hasCookie(SESSIONID)) {
            HttpSession httpSession = HttpSessionStorage.getSession(this.getSessionId());
            if (httpSession == null) {
                httpSession = HttpSessionStorage.create();
            }
            return httpSession;
        } else {
            return HttpSessionStorage.create();
        }
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public String getBodyValue(String key) {
        return this.requestBody.getValue(key.toLowerCase());
    }

    public boolean headerContainsValueOf(HeaderParam key, String value) {
        return this.requestHeader.containsValueOf(key, value);
    }

    public RequestMethod getRequestMethod() {
        return this.requestLine.getMethod();
    }

    public boolean hasCookie(String key) {
        return this.requestHeader.hasCookie(key);
    }

    public String getSessionId() {
        return this.requestHeader.getCookie(SESSIONID);
    }
}
