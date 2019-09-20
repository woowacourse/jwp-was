package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Arrays;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String HTTP_VERSION = "HTTP/1.1";

    private final OutputStream out;
    private HttpStatus httpStatus;
    private final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    private byte[] body;

    public HttpResponse(final OutputStream out) {
        this.out = out;
    }

    public void forward(String resource) {
        forward(resource, HttpStatus.OK);
    }

    public void forward(String resource, HttpStatus httpStatus) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(resource);
            setStatus(httpStatus);
            setHeader("Content-Type", MimeType.getType(parseExtension(resource)));
            setHeader("Content-Length", String.valueOf(body.length));
            setBody(body);
            send();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private String parseExtension(String resource) {
        int beginIndex = resource.lastIndexOf(".") + 1;
        return resource.substring(beginIndex);
    }

    public void sendRedirect(String location) {
        sendRedirect(location, HttpStatus.FOUND);
    }

    public void sendRedirect(String location, HttpStatus httpStatus) {
        setStatus(httpStatus);
        setHeader("Location", location);
        send();
    }

    public void setHeader(String name, String... values) {
        headers.put(name, Arrays.asList(values));
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setStatus(HttpStatus httpStatus){
        this.httpStatus = httpStatus;
    }

    public void send() {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            writeStartLine(dos);
            writeHeader(dos);
            writeBody(dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeStartLine(final DataOutputStream dos) throws IOException {
        dos.writeBytes(String.format("%s %s %s\n", HTTP_VERSION, httpStatus.getCode(), httpStatus.getPhrase()));
    }

    private void writeHeader(final DataOutputStream dos) throws IOException {
        for (String key : headers.keySet()) {
            dos.writeBytes(String.format("%s: %s\n", key, headers.get(key)));
        }
        dos.writeBytes("\n");
    }

    private void writeBody(final DataOutputStream dos) throws IOException {
        if (body != null) {
            dos.write(body, 0, body.length);
        }
    }
}
