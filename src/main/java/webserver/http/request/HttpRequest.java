package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import db.SessionDataBase;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;
    private final HttpSession httpSession;

    private HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody,
        HttpSession httpSession) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
        this.httpSession = httpSession;
    }

    public static HttpRequest of(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        RequestLine requestLine = RequestLine.of(bufferedReader);
        RequestHeader requestHeader = RequestHeader.of(bufferedReader);
        RequestBody requestBody = RequestBody.of(bufferedReader, requestHeader.getContentLength());
        HttpSession httpSession = getHttpSession(requestHeader);

        return new HttpRequest(requestLine, requestHeader, requestBody, httpSession);
    }

    private static HttpSession getHttpSession(RequestHeader requestHeader) {
        String jSessionId = requestHeader.getCookieValue("JSESSIONID");
        if (jSessionId.isEmpty()) {
            return HttpSession.create();
        }
        return SessionDataBase.findHttpSessionById(jSessionId);
    }

    public String getCookieValue(String cookieName) {
        return requestHeader.getCookieValue(cookieName);
    }

    public String getDefaultPath() {
        return requestLine.getPath();
    }

    public Map<String, String> getParameters() {
        return requestLine.getParameters();
    }

    public String getBody() {
        return requestBody.getBody();
    }

    public boolean isGet() {
        return requestLine.getMethod() == Method.GET;
    }

    public boolean isPost() {
        return requestLine.getMethod() == Method.POST;
    }

    public boolean isPut() {
        return requestLine.getMethod() == Method.PUT;
    }

    public boolean isDelete() {
        return requestLine.getMethod() == Method.DELETE;
    }

    public String getMethod() {
        return requestLine.getMethod().getMethodName();
    }

    public String getSessionId() {
        return httpSession.getId();
    }
}
