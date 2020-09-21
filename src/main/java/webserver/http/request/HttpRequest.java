package webserver.http.request;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpRequest {
    private RequestLine requestLine;
    private RequestHeaders requestHeaders;
    private RequestBody requestBody;

    public HttpRequest(RequestLine requestLine, RequestHeaders requestHeaders, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        if (requestLine.allowBody()) {
            this.requestBody = requestBody;
        }
    }

    public RequestMethod getHttpMethod() {
        return requestLine.getHttpMethod();
    }

    public String getUrl() {
        return getRequestUrl().getUrl();
    }

    public String getParameter(String key) {
        List<String> parameters = getParameters(key);
        if (Objects.isNull(parameters) || parameters.isEmpty()) {
            return null;
        }
        return parameters.get(0);
    }

    public List<String> getParameters(String key) {
        List<String> values = Stream.of(getRequestParameter().getParameters(key), requestBody.getParameters(key))
            .filter(Objects::nonNull)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
        return values.isEmpty() ? null : values;
    }

    public String getHttpVersion() {
        return requestLine.getHttpVersion();
    }

    public String getHeader(String key) {
        return requestHeaders.get(key);
    }

    public RequestUrl getRequestUrl() {
        return requestLine.getHttpUrl();
    }

    public RequestParams getRequestParameter() {
        return getRequestUrl().getHttpRequestParams();
    }

    public String getBody() {
        return requestBody.getBody();
    }
}
