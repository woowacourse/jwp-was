package http.response;

import http.HttpStatus;
import http.MediaType;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

//TODO: static of Method 고려 해볼 것

/**
 * 그렇네요:) of와 같은 static 메서드를 만들어 HttpStatus, uri, header 등의 정보를 받아
 * httpResponse 인스턴스를 반환해주는 건 어떨까요~? HttpStatus라는 클래스가 하나 더 생길 수 있겠네요.
 * 의견 부탁드립니다:)
 */
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
