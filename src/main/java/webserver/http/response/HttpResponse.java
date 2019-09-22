package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.HttpStatus;
import webserver.http.MimeType;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static webserver.http.HttpHeaders.*;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private static final String HTTP_VERSION = "HTTP/1.1";

    private final Map<String, String> headers = new HashMap<>();
    private final OutputStream out;
    private HttpStatus httpStatus;
    private byte[] body;

    public HttpResponse(final OutputStream out) {
        this.out = out;
    }

    public void forward(final String resource) {
        forward(resource, HttpStatus.OK);
    }

    public void forward(final String resource, final HttpStatus httpStatus) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(resource);
            setStatus(httpStatus);
            setHeader(CONTENT_TYPE, MimeType.getType(parseExtension(resource)));
            setHeader(CONTENT_LENGTH, String.valueOf(body.length));
            setBody(body);
            write();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private String parseExtension(final String resource) {
        int beginIndex = resource.lastIndexOf(".") + 1;
        return resource.substring(beginIndex);
    }

    public void sendRedirect(final String location) {
        sendRedirect(location, HttpStatus.FOUND);
    }

    public void sendRedirect(final String location, final HttpStatus httpStatus) {
        setStatus(httpStatus);
        setHeader(LOCATION, location);
        write();
    }

    public void sendError(final HttpStatus httpStatus) {
        setStatus(httpStatus);
        write();
    }

    public void sendError(final HttpStatus httpStatus, final String message) {
        log.error(message);
        setStatus(httpStatus);
        setBody(message.getBytes());
        write();
    }

    private void write() {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            writeStartLine(dos);
            writeHeader(dos);
            writeBody(dos);
        } catch (IOException e) {
            log.error(e.getMessage());
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

    public void setHeader(final String name, final String value) {
        headers.put(name, value);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setStatus(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
