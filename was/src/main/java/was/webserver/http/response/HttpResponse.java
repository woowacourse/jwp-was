package was.webserver.http.response;

import was.utils.FileIoUtils;
import was.webserver.http.ContentType;
import was.webserver.http.HttpHeaderType;
import was.webserver.http.HttpHeaders;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class HttpResponse {
    private DataOutputStream dos;
    private HttpResponseStartLine httpResponseStartLine;
    private HttpHeaders httpHeaders;
    private HttpResponseBody httpResponseBody;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
        this.httpResponseStartLine = HttpResponseStartLine.defaultStartLine();
        this.httpHeaders = HttpHeaders.emptyHeaders();
        this.httpResponseBody = HttpResponseBody.emptyResponseBody();
    }

    public void forward(String path) throws IOException, URISyntaxException {
        ContentType contentType = ContentType.from(path);
        String filePath = contentType.getDirectory() + path;
        byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
        response200Header(body, contentType);
        responseBody(body);
        response();
    }

    private void response200Header(byte[] body, ContentType contentType) {
        addHeader(HttpHeaderType.CONTENT_TYPE.getType(), contentType.getContentType());
        addHeader(HttpHeaderType.CONTENT_LENGTH.getType(), String.valueOf(body.length));
    }

    private void responseBody(byte[] body) {
        httpResponseBody = HttpResponseBody.add(body);
    }

    public void forward(byte[] body) throws IOException {
        response200Header(body, ContentType.HTML);
        responseBody(body);
        response();
    }

    public void sendRedirect(String location) throws IOException {
        response302StartLine();
        response302Header(location);
        response();
    }

    private void response302StartLine() {
        httpResponseStartLine.update(HttpStatusCode.FOUND);
    }

    private void response302Header(String location) {
        addHeader(HttpHeaderType.LOCATION.getType(), location);
    }

    public void addHeader(String type, String content) {
        httpHeaders.add(type, content);
    }

    private void response() throws IOException {
        dos.writeBytes(httpResponseStartLine.getHttpResponseStartLineString() + System.lineSeparator());
        dos.writeBytes(httpHeaders.getHttpHeadersString() + System.lineSeparator());
        if (httpResponseBody.isEmpty()) {
            dos.flush();
            return;
        }
        dos.write(httpResponseBody.getBody(), 0, httpResponseBody.getLength());
        dos.flush();
    }
}
