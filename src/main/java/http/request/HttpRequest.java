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
        this.httpRequestHeader = new HttpRequestHeader(lines.subList(1, getToIndex(lines)));
        log.debug("Request Header: {}", httpRequestHeader);

        if (httpRequestHeader.getContentLength() != 0) {
            this.httpRequestBody = new HttpRequestBody(lines.subList(getToIndex(lines) + 1, lines.size()));
            log.debug("Request Body: {}", httpRequestBody.getBody());
        }
    }

    private int getToIndex(List<String> lines) {
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
        int lastIndex = httpRequestLine.getUri().lastIndexOf("/");
        String extension = httpRequestLine.getUri().substring(lastIndex + 1);
        return MediaType.isContain(extension);
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
