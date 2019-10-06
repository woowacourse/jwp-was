package webserver.controller.request;

import webserver.controller.cookie.HttpCookie;
import webserver.controller.request.header.HttpHeaderFields;
import webserver.controller.request.header.HttpMethod;
import webserver.controller.request.header.HttpRequestLine;
import webserver.controller.session.HttpSession;
import webserver.controller.session.HttpSessionManager;
import webserver.controller.session.UUIDGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpRequest {
    private final HttpRequestLine httpRequestLine;
    private final HttpHeaderFields httpHeaderFields;
    private final Map<String, String> httpRequestBodyFields;
    private HttpCookie httpCookie;

    public HttpRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String[] requestline = HttpRequestParser.parseRequestLine(bufferedReader);

        this.httpRequestLine = new HttpRequestLine(requestline);
        this.httpHeaderFields = new HttpHeaderFields(HttpRequestParser.parseHeaderFields(bufferedReader));
        this.httpRequestBodyFields = setRequestBody(bufferedReader);
        this.httpCookie = createCookie();
    }

    private HttpCookie createCookie() {
        HttpSessionManager httpSessionManager = HttpSessionManager.getInstance();
        if(isFirstRequest()) {
            UUID uuid = UUIDGenerator.generateUUID();
            httpSessionManager.addSession(new HttpSession(uuid));
            return new HttpCookie(uuid);
        }
        String cookieValue = this.httpHeaderFields.getHeaderFieldValue("Cookie");
        return new HttpCookie(cookieValue);
    }

    public String getSessionId() {
        return this.httpCookie.getSessionId();
    }

    public boolean isFirstRequest() {
        return httpHeaderFields.doesNotHaveCookie();
    }

    private Map<String, String> setRequestBody(BufferedReader bufferedReader) throws IOException {
        HttpMethod httpMethod = httpRequestLine.getHttpMethod();
        if (httpMethod == HttpMethod.POST || httpMethod == HttpMethod.PUT) {
            return HttpRequestParser.parseBody(bufferedReader, httpHeaderFields.getContentLength());
        }
        return new HashMap<>();
    }

    public MimeType getMimeType() {
        return httpRequestLine.getMimeType();
    }

    public String getPath() {
        return httpRequestLine.getUrl();
    }

    public String getVersion() {
        return httpRequestLine.getVersion();
    }

    public HttpMethod getHttpMethod() {
        return httpRequestLine.getHttpMethod();
    }

    public Map<String, String> getBodyFields() {
        return this.httpRequestBodyFields;
    }

    public String getHeaderFieldValue(String key) {
        return httpHeaderFields.getHeaderFieldValue(key);
    }

    public void setSessionId(String sessionId) {
        this.httpCookie.setSessionId(sessionId);
    }

    public String getCookieValues() {
        return this.httpCookie.getCookieValues();
    }

    public Map<String, String> getCookieFields() {
        return this.httpCookie.getFields();
    }

    public HttpCookie getHttpCookie() {
        return httpCookie;
    }

    public boolean isLogined() {
        return this.httpCookie.isLogined();
    }
}
