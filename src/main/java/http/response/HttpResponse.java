package http.response;

import http.MediaType;

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

    public void sendRedirect(String uri) {
        this.httpStatusLine = new HttpStatusLine("HTTP/1.1 302 FOUND\r\n");
        httpResponseHeader.addHeader("Location", "/" + uri);
    }

    public void send200Ok(String uri) throws IOException, URISyntaxException {
        String contentType = MediaType.getContentType(uri);

        this.httpStatusLine = new HttpStatusLine("HTTP/1.1 200 OK\r\n");
        this.httpResponseBody = new HttpResponseBody(uri);
        httpResponseHeader.addHeader("Content-Type", contentType + ";charset=utf-8");
        httpResponseHeader.addHeader("Content-Length", String.valueOf(httpResponseBody.getBodyLength()));
    }

    public void send404Error() {
        this.httpStatusLine = new HttpStatusLine("HTTP/1.1 404 NOT FOUND\r\n");
    }
}
