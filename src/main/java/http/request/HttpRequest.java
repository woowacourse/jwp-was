package http.request;

import http.common.*;

import java.util.List;
import java.util.Optional;

public class HttpRequest {
    private static final String URL_FORMAT = "%s://%s%s";
    private static final String HOST_KEY = "Host";
    private static final String JSESSIONID = "JSESSIONID";

    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private Parameters parameters;
    private List<Cookie> cookies;

    private HttpRequest(RequestLine requestLine, RequestHeader requestHeader, Parameters parameters, List<Cookie> cookies) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.parameters = parameters;
        this.cookies = cookies;
    }

    public static HttpRequest of(RequestLine requestLine, RequestHeader requestHeader,
                                 Parameters parameters, List<Cookie> cookies) {
        return new HttpRequest(requestLine, requestHeader, parameters, cookies);
    }

    public boolean isGet() {
        return requestLine.getMethod().equals(HttpMethod.GET);
    }

    public boolean isPost() {
        return requestLine.getMethod().equals(HttpMethod.POST);
    }

    public String getUrl() {
        String path = requestLine.getPath();
        String protocol = requestLine.getProtocol();
        String host = requestHeader.getHeader(HOST_KEY);

        return String.format(URL_FORMAT, protocol, host, path);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    public String getHeader(String headerKey) {
        return requestHeader.getHeader(headerKey);
    }

    public String getParameter(String key) {
        return parameters.getParameter(key);
    }

    public HttpSession getSession() {
        Optional<Cookie> cookie = cookies.stream()
                .filter(c -> c.getName().equals(JSESSIONID))
                .findAny();
        if (cookie.isPresent()) {
            return SessionManager.getSession(cookie.get().getValue());
        }
        return SessionManager.createSession();
    }
}
