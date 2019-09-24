package http.response;

import http.common.HttpHeader;
import http.common.HttpVersion;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class HttpResponse {
    private StatusLine statusLine;
    private HttpHeader httpHeader;
    private HttpResponseBody httpResponseBody;

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public HttpResponseBody getHttpResponseBody() {
        return httpResponseBody;
    }

    public void forward(String url) {
        this.statusLine = new StatusLine(HttpStatus.OK, HttpVersion.HTTP_1_1);
        this.httpHeader = HttpHeader.of(Arrays.asList("Content-Type: " + HttpContentType.of(url).getContentType()));

        try {
            this.httpResponseBody = HttpResponseBody.of(FileIoUtils.loadFileFromClasspath(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void redirect(String url) {
        this.statusLine = new StatusLine(HttpStatus.FOUND, HttpVersion.HTTP_1_1);
        this.httpHeader = HttpHeader.redirect(url);
        this.httpResponseBody = HttpResponseBody.empty();
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "statusLine=" + statusLine +
                ", httpHeader=" + httpHeader +
                ", httpResponseBody=" + httpResponseBody +
                '}';
    }
}
