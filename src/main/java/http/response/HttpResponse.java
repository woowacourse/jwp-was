package http.response;

import com.google.common.base.Charsets;
import http.support.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.view.View;
import utils.FileIoUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String HTTP_VERSION = "HTTP/1.1 ";
    private static final String DELIMITER_OF_RESPONSE_HEADER = ": ";
    private static final Charset DEFAULT_CHARSET = Charsets.UTF_8;

    private final Map<String, String> headers = new HashMap<>();
    private final List<String> cookies = new ArrayList<>();
    private final OutputStream outputStream;

    public HttpResponse(final OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void addHeader(final String key, final String value) {
        headers.put(key, value);
    }

    public void addCookie(final String key, final String value) {
        cookies.add(key + "=" + value);
    }

    public void forward(final View view) throws IOException {
        byte[] body = view.render().getBytes(DEFAULT_CHARSET);
        addHeader("Content-Length", Integer.toString(body.length));

        writeStartLine(StatusCode.OK);
        writeHeaders();
        writeCookies();
        writeNewLine();

        outputStream.write(body, 0, body.length);
        outputStream.flush();
    }

    private void writeNewLine() throws IOException {
        outputStream.write("\r\n".getBytes(Charsets.UTF_8));
    }

    public void forward(String path) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(path);
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

    private void writeStartLine(StatusCode statusCode) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HTTP_VERSION);
        stringBuilder.append(statusCode.getStatusCode());
        stringBuilder.append(" ");
        stringBuilder.append(statusCode);
        stringBuilder.append("\r\n");
        outputStream.write(stringBuilder.toString().getBytes(Charsets.UTF_8));
    }

    private void writeHeaders() throws IOException {
        for (String key : headers.keySet()) {
            outputStream.write(key.getBytes(Charsets.UTF_8));
            outputStream.write(DELIMITER_OF_RESPONSE_HEADER.getBytes(Charsets.UTF_8));
            outputStream.write(headers.get(key).getBytes(Charsets.UTF_8));
            outputStream.write("\r\n".getBytes(Charsets.UTF_8));
        }
    }

    private void writeCookies() throws IOException {
        String cookies = String.join("; ", this.cookies);
        outputStream.write(String.format("Set-Cookie: %s", cookies).getBytes());
        writeNewLine();
    }
}
