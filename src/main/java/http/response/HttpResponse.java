package http.response;

import com.google.common.base.Charsets;
import http.support.StatusCode;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class HttpResponse {
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String DELIMITER_OF_RESPONSE_HEADER = ": ";
    private static final Charset DEFAULT_CHARSET = Charsets.UTF_8;

    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> cookies = new HashMap<>();
    private final OutputStream outputStream;

    public HttpResponse(final OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void addHeader(final String key, final String value) {
        headers.put(key, value);
    }

    public void addCookie(final String key, final String value) {
        cookies.put(key, value);
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
        for (String key : headers.keySet()) {
            outputStream.write(key.getBytes(DEFAULT_CHARSET));
            outputStream.write(DELIMITER_OF_RESPONSE_HEADER.getBytes(DEFAULT_CHARSET));
            outputStream.write(headers.get(key).getBytes(DEFAULT_CHARSET));
            outputStream.write("\r\n".getBytes(DEFAULT_CHARSET));
        }
    }

    private void writeCookies() throws IOException {
        final List<String> parsedCookies = this.cookies.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue() + ";")
                .collect(toList());
        final String result = String.join(" ", parsedCookies);
        outputStream.write(String.format("Set-Cookie: %s", result).getBytes());
        writeNewLine();
    }

    private void writeNewLine() throws IOException {
        outputStream.write("\r\n".getBytes(DEFAULT_CHARSET));
    }
}
