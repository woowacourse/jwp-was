package webserver.http.response;
import webserver.http.common.HttpHeader;
import webserver.http.common.HttpVersion;

import java.util.Arrays;

public class HttpResponse {
    private StartLine startLine;
    private HttpHeader httpHeader;
    private HttpResponseBody httpResponseBody;

    public void redirect(final String url) {
        this.init(
                new StartLine(HttpVersion.HTTP_1_1, HttpStatus.FOUND),
                HttpHeader.of(Arrays.asList("Location: " + url)),
                HttpResponseBody.empty()
        );
    }

    public void forward(final String url, final byte[] file) {
        this.init(
                new StartLine(HttpVersion.HTTP_1_1, HttpStatus.OK),
                HttpHeader.of(Arrays.asList("Content-Type: " + HttpContentType.findContentType(url))),
                HttpResponseBody.of(file)
        );
    }

    private void init(final StartLine startLine, final HttpHeader httpHeader, final HttpResponseBody httpResponseBody) {
        this.startLine = startLine;
        this.httpHeader = httpHeader;
        this.httpResponseBody = httpResponseBody;
    }

    public StartLine getStartLine() {
        return startLine;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public HttpResponseBody getHttpResponseBody() {
        return httpResponseBody;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "startLine=" + startLine +
                ", httpHeader=" + httpHeader +
                ", httpResponseBody=" + httpResponseBody +
                '}';
    }
}
