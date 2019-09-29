package http.response;

import com.google.common.base.Charsets;
import http.request.HttpCookie;
import http.request.HttpHeader;
import http.support.StatusCode;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class HttpResponse {
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final Charset DEFAULT_CHARSET = Charsets.UTF_8;

    private final HttpHeader httpHeader = HttpHeader.empty();
    private final HttpCookie httpCookie = new HttpCookie();
    private final OutputStream outputStream;

    public HttpResponse(final OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void addHeader(final String key, final String value) {
        httpHeader.addHeader(key, value);
    }

    public void addCookie(final String name, final String value) {
        httpCookie.addCookie(name, value);
    }

    public void forward(final byte[] body) throws IOException {
        addHeader("Content-Length", Integer.toString(body.length));

        writeStartLine(StatusCode.OK);
        writeHeaders();
        writeCookies();
        writeNewLine();

        outputStream.write(body, 0, body.length);
        outputStream.flush();
    }

    public void sendRedirect() throws IOException {
        writeStartLine(StatusCode.FOUND);
        writeHeaders();
        writeCookies();
        outputStream.flush();
    }

    private void writeStartLine(final StatusCode statusCode) throws IOException {
        String startLine = String.format("%s %s %s\r\n", HTTP_VERSION, statusCode.getStatusCode(), statusCode);
        outputStream.write(startLine.getBytes(DEFAULT_CHARSET));
    }

    private void writeHeaders() throws IOException {
        outputStream.write(httpHeader.parse().getBytes(DEFAULT_CHARSET));
    }

    private void writeCookies() throws IOException {
        outputStream.write(String.format("Set-Cookie: %s", httpCookie.parse()).getBytes());
        writeNewLine();
    }

    private void writeNewLine() throws IOException {
        outputStream.write("\r\n".getBytes(DEFAULT_CHARSET));
    }
}
