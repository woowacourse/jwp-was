package http;

import com.google.common.base.Charsets;
import http.support.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String HTTP_VERSION = "HTTP/1.1 ";
    private static final String DELIMITER_OF_RESPONSE_HEADER = ": ";

    private Map<String, String> headers = new HashMap<>();
    private OutputStream outputStream;

    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    //@TODO
    public void forward(String path) throws IOException, URISyntaxException {
        logger.debug("Forwarding Path : {}", path);

        byte[] body = FileIoUtils.loadFileFromClasspath(path);
        addHeader("Content-Length", Integer.toString(body.length));

        writeStartLine(StatusCode.OK);
        writeHeaders();

        outputStream.write(body, 0, body.length);
        outputStream.flush();
    }

    // @TODO
    public void sendRedirect() throws IOException {
        writeStartLine(StatusCode.FOUND);
        writeHeaders();
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
        outputStream.write("\r\n".getBytes(Charsets.UTF_8));
    }
}
