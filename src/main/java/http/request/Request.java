package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import http.ContentType;
import http.HttpHeaders;
import http.HttpMethod;
import http.session.HttpSession;
import http.session.HttpSessionStore;
import http.session.WebSession;
import utils.IOUtils;

public class Request {
    private static final String DELIMITER = ": ";
    private static final WebSession DEFAULT_SESSION = new WebSession();

    private final RequestLine requestLine;
    private final HttpHeaders httpHeader;
    private final RequestBody requestBody;
    private final Cookies cookies;
    private HttpSession httpSession;

    public Request(BufferedReader br) throws Exception {
        this.requestLine = new RequestLine(br);
        this.httpHeader = new HttpHeaders(ofRequestHeader(br));
        this.requestBody = new RequestBody(br, httpHeader.getContentLength());
        this.cookies = new Cookies(httpHeader.getHeader(HttpHeaders.COOKIE));
        this.httpSession = DEFAULT_SESSION;
        if (!"".equals(cookies.getCookieValue(WebSession.DEFAULT_SESSION_COOKIE_NAME))) {
            this.httpSession = HttpSessionStore.getSession(
                    cookies.getCookieValue(WebSession.DEFAULT_SESSION_COOKIE_NAME));
        }
        IOUtils.printRequest(this);
    }

    private Map<String, String> ofRequestHeader(BufferedReader br) throws IOException {
        String line = br.readLine();
        Map<String, String> requestHeaders = new HashMap<>();
        while (line != null && !"".equals(line)) {
            String[] token = line.split(DELIMITER);
            requestHeaders.put(token[0], token[1]);
            line = br.readLine();
        }

        return requestHeaders;
    }

    public boolean isMethod(HttpMethod httpMethod) {
        return httpMethod == requestLine.getMethod();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getRequestHeaders() {
        return Collections.unmodifiableMap(httpHeader.getHttpHeaders());
    }

    public String getHeader(String key) {
        return httpHeader.getHeader(key);
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public QueryParams getQueryParams() {
        return requestLine.getQueryParams();
    }

    public HttpMethod getRequestMethod() {
        return requestLine.getMethod();
    }

    public String getContentType() {
        return ContentType.of(getPath()).getContentType();
    }

    public HttpSession getHttpSession(boolean create) {
        if (create) {
            httpSession = HttpSessionStore.create();
        }
        return httpSession;
    }
}
