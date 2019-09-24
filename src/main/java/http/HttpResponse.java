package http;

import com.google.common.base.Charsets;
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

    private Map<String, String> headers = new HashMap<>();
    private OutputStream outputStream;

    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void forward(String path) throws IOException, URISyntaxException {
        logger.debug("Forwarding Path : {}", path);

        byte[] body = FileIoUtils.loadFileFromClasspath(path);
        addHeader("Content-Length", Integer.toString(body.length));

        outputStream.write("HTTP/1.1 200 OK \r\n".getBytes(Charsets.UTF_8));
        writeHeaders();

        outputStream.write(body, 0, body.length);
        outputStream.flush();
    }

    public void sendRedirect() throws IOException {
        outputStream.write("HTTP/1.1 302 FOUND \r\n".getBytes(Charsets.UTF_8));
        writeHeaders();
        outputStream.flush();
    }

    private void writeHeaders() throws IOException {
        for (String key : headers.keySet()) {
            outputStream.write(key.getBytes(Charsets.UTF_8));
            outputStream.write(": ".getBytes(Charsets.UTF_8));
            outputStream.write(headers.get(key).getBytes(Charsets.UTF_8));
            outputStream.write("\r\n".getBytes(Charsets.UTF_8));
        }
        outputStream.write("\r\n".getBytes(Charsets.UTF_8));
    }
}
