package http.model;

import http.session.HttpSession;

import java.util.Map;

public class ServletRequest {
    private HttpMethod httpMethod;
    private HttpUri httpUri;
    private HttpProtocols httpProtocols;
    private HttpHeaders httpHeaders;
    private HttpParameters httpParameters;
    private HttpSession session;

    private ServletRequest(HttpMethod httpMethod, HttpUri httpUri, HttpProtocols httpProtocols,
                           HttpHeaders httpHeaders, HttpParameters httpParameters) {
        this.httpMethod = httpMethod;
        this.httpUri = httpUri;
        this.httpProtocols = httpProtocols;
        this.httpHeaders = httpHeaders;
        this.httpParameters = httpParameters;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void bindSession(HttpSession session) {
        this.session = session;
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

        public Builder method(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public Builder uri(String uri) {
            this.httpUri = new HttpUri(uri);
            return this;
        }

        public Builder protocol(String protocol) {
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

        public ServletRequest build() {
            if (httpHeaders == null) {
                httpHeaders = new HttpHeaders();
            }
            if (httpParameters == null) {
                httpParameters = new HttpParameters();
            }
            return new ServletRequest(this.httpMethod, this.httpUri, this.httpProtocols, this.httpHeaders, this.httpParameters);
        }
    }
}
