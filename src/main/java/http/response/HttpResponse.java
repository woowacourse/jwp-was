package http.response;

import http.HttpStatus;
import http.MediaType;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpResponse {
    private HttpStatusLine httpStatusLine;
    private HttpResponseHeader httpResponseHeader;
    private HttpResponseBody httpResponseBody;

    public HttpResponse() {
        this.httpResponseHeader = new HttpResponseHeader();
    }

    public HttpStatusLine getHttpStatusLine() {
        return httpStatusLine;
    }

    public HttpResponseHeader getHttpResponseHeader() {
        return httpResponseHeader;
    }

    public HttpResponseBody getHttpResponseBody() {
        return httpResponseBody;
    }

    public void redirect(String uri) {
        this.httpStatusLine = new HttpStatusLine(HttpStatus.FOUND);
        httpResponseHeader.addHeader("Location", uri);
    }

    public void setStatusCode(HttpStatus httpStatus) {
        this.httpStatusLine = new HttpStatusLine(httpStatus);
    }

    public void forward(String uri) throws IOException, URISyntaxException {
        String contentType = MediaType.getContentType(uri);

        this.httpResponseBody = new HttpResponseBody(FileIoUtils.loadFileFromClasspath(MediaType.getFullPath(uri)));
        httpResponseHeader.addHeader("Content-Type", contentType + ";charset=utf-8");
        httpResponseHeader.addHeader("Content-Length", String.valueOf(httpResponseBody.getBodyLength()));
    }

    public void setCookie(String cookie) {
        httpResponseHeader.addHeader("Set-Cookie", cookie);
    }

    public void setBody(String htmlBody) {
        this.httpResponseBody = new HttpResponseBody(htmlBody.getBytes());
    }
}
