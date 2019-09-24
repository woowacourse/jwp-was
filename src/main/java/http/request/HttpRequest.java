package http.request;

import http.HttpMethod;
import http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private HttpRequestLine httpRequestLine;
    private HttpRequestHeader httpRequestHeader;
    private HttpRequestBody httpRequestBody;

    public HttpRequest(List<String> lines) {
        this.httpRequestLine = new HttpRequestLine(lines.get(0));
        createHeader(lines);
        createBody(lines);
    }

    private void createHeader(List<String> lines) {
        int lastIndex = getLastIndex(lines);

        this.httpRequestHeader = new HttpRequestHeader(lines.subList(1, lastIndex));
        log.debug("Request Header: {}", httpRequestHeader);
    }

    private void createBody(List<String> lines) {
        int lastIndex = getLastIndex(lines);

        if (httpRequestHeader.getContentLength() != 0) {
            this.httpRequestBody = new HttpRequestBody(lines.subList(lastIndex + 1, lines.size()));
            log.debug("Request Body: {}", httpRequestBody.getBody());
        }
    }

    private int getLastIndex(List<String> lines) {
        int lastIndex = 0;
        String line = lines.get(0);
        while (!"".equals(line)) {
            lastIndex++;
            line = lines.get(lastIndex);
        }
        return lastIndex;
    }

    public HttpRequestLine getHttpRequestLine() {
        return httpRequestLine;
    }

    public boolean isContainExtension() {
        return MediaType.isContain(httpRequestLine.getUri());
    }

    public List<String> getHttpRequestBody() {
        return httpRequestBody.getBody();
    }

    public HttpMethod getMethod() {
        return httpRequestLine.getMethod();
    }

    public String getUri() {
        return httpRequestLine.getUri();
    }
}
