package http.request;

import http.HttpHeaders;
import http.HttpMimeType;
import http.HttpVersion;
import utils.ExtensionParser;

import static http.HttpHeaders.ACCEPT;
import static http.HttpHeaders.CONTENT_TYPE;
import static http.HttpMimeType.X_WWW_FORM_URLENCODED;

public class HttpRequest {
    private HttpRequestLine requestLine;
    private HttpHeaders headers;
    private String body;
    private QueryParams queryParams;

    HttpRequest(HttpRequestLine requestLine, HttpHeaders headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
        this.queryParams = QueryParams.of(getParams());
    }

    private String getParams() {
        if (HttpMethod.GET.match(requestLine.getMethod())) {
            return requestLine.getUri().getQueryParams();
        }
        if (existQueryParamsInBody()) {
            return body;
        }
        return null;
    }

    private boolean existQueryParamsInBody() {
        return X_WWW_FORM_URLENCODED.match(headers.getHeader(CONTENT_TYPE));
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public HttpUri getUri() {
        return requestLine.getUri();
    }

    public HttpVersion getVersion() {
        return requestLine.getVersion();
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public String getParam(String key) {
        return queryParams.getParam(key);
    }

    public HttpMimeType getMimeType() {
        String accept = headers.getHeader(ACCEPT);
        String extension = ExtensionParser.parse(requestLine.getUri().getPath());
        return HttpMimeType.getMimeTypeFrom(accept, extension);
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return requestLine + "\n" + headers + "\n";
    }
}
