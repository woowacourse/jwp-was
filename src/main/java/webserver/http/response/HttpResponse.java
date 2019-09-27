package webserver.http.response;

import utils.FileIoUtils;
import webserver.http.common.HttpHeader;
import webserver.http.common.HttpVersion;
import webserver.http.request.HttpRequestType;

public class HttpResponse {
    private StatusLine statusLine;
    private HttpHeader httpHeader;
    private HttpResponseBody httpResponseBody;

    public HttpResponse() {
        statusLine = new StatusLine(HttpVersion.HTTP_1_1);
        httpHeader = new HttpHeader();
        httpResponseBody = HttpResponseBody.empty();
    }

    public void redirect(final String url) {
        setHttpStatus(HttpStatus.FOUND);
        addHeader("Location", url);
    }

    public void forward(final String url, final byte[] file) {
        setContentType(url);
        setHttpStatus(HttpStatus.OK);
        httpResponseBody = HttpResponseBody.of(file);
    }
    public void forward(final String url) {
        forward(url, FileIoUtils.loadFileFromClasspath(HttpRequestType.redefineUrl(url)));
    }

    public void setContentType(final String url) {
        addHeader("Content-Type", HttpContentType.findContentType(url));
    }

    public void addHeader(final String key, final String value) {
        httpHeader.put(key, value);
    }

    public void setHttpStatus(final HttpStatus httpStatus) {
        this.statusLine.setHttpStatus(httpStatus);
    }

    public StatusLine getStatusLine() {
        return statusLine;
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
                "startLine=" + statusLine +
                ", httpHeader=" + httpHeader +
                ", httpResponseBody=" + httpResponseBody +
                '}';
    }
}
