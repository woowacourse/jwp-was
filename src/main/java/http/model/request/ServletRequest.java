package http.model.request;

import http.model.common.HttpCookie;
import http.model.common.HttpHeaders;
import http.model.common.HttpProtocols;
import http.session.HttpSession;
import http.session.SessionManager;

import java.util.Map;

public class ServletRequest {
    private static final String COOKIE = "Cookie";
    private SessionManager sessionManager;
    private HttpMethod httpMethod;
    private HttpUri httpUri;
    private HttpProtocols httpProtocols;
    private HttpHeaders httpHeaders;
    private HttpParameters httpParameters;
    private HttpCookie httpCookie;

    private ServletRequest(HttpMethod httpMethod, HttpUri httpUri, HttpProtocols httpProtocols,
                           HttpHeaders httpHeaders, HttpParameters httpParameters, HttpCookie httpCookie) {
        this.httpMethod = httpMethod;
        this.httpUri = httpUri;
        this.httpProtocols = httpProtocols;
        this.httpHeaders = httpHeaders;
        this.httpParameters = httpParameters;
        this.httpCookie = httpCookie;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean hasCookie() {
        return httpHeaders.getHeader(COOKIE) != null;
    }

    public String getCookie() {
        return httpHeaders.getHeader(COOKIE);
    }

    public HttpSession getSession() {
        return this.sessionManager.getSession();
    }

    public void bindSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpUri getHttpUri() {
        return httpUri;
    }

    public HttpProtocols getHttpProtocols() {
        return httpProtocols;
    }

    public String getHeader(String key) {
        return httpHeaders.getHeader(key);
    }

    public String getParameter(String key) {
        return httpParameters.getParameter(key);
    }

    public Map<String, String> getHeaders() {
        return httpHeaders.getHeaders();
    }

    public Map<String, String> getParameters() {
        return httpParameters.getParameters();
    }

    public static class Builder {
        private HttpMethod httpMethod;
        private HttpUri httpUri;
        private HttpProtocols httpProtocols;
        private HttpHeaders httpHeaders;
        private HttpParameters httpParameters;
        private HttpCookie httpCookie;

        public Builder requestLine(HttpMethod httpMethod, String uri, String protocol) {
            this.httpMethod = httpMethod;
            this.httpUri = new HttpUri(uri);
            this.httpProtocols = HttpProtocols.of(protocol);
            return this;
        }

        public Builder headers(Map<String, String> header) {
            this.httpHeaders = new HttpHeaders(header);
            return this;
        }

        public Builder params(Map<String, String> params) {
            this.httpParameters = new HttpParameters(params);
            return this;
        }

        public Builder cookie(Map<String, String> cookie) {
            this.httpCookie = new HttpCookie(cookie);
            return this;
        }

        public ServletRequest build() {
            if (httpHeaders == null) {
                httpHeaders = new HttpHeaders();
            }
            if (httpParameters == null) {
                httpParameters = new HttpParameters();
            }
            if (httpCookie == null) {
                httpCookie = new HttpCookie();
            }
            return new ServletRequest(this.httpMethod, this.httpUri, this.httpProtocols, this.httpHeaders, this.httpParameters, this.httpCookie);
        }
    }
}
