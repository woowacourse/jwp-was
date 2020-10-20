package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.EntityHeader;
import webserver.HttpField;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private final static Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final DataOutputStream dos;
    private final Map<HttpField, String> headers;

    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
        headers = new HashMap<>();
    }

    public void addHeader(HttpField http, String value) {
        headers.put(http, value);
    }

    public void sendError(Status status) {
        try {
            processStatusLine(status);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendRedirect(String location) {
        try {
            processStatusLine(Status.FOUND);
            processHeader(ResponseHeader.LOCATION, location);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void forward(String path) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(path);
            response200Header(body);
            responseBody(body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(byte[] body) throws IOException {
        processStatusLine(Status.OK);
        processHeader(EntityHeader.CONTENT_TYPE, headers.get(EntityHeader.CONTENT_TYPE));
        processHeader(EntityHeader.CONTENT_LENGTH, String.valueOf(body.length));
        dos.writeBytes("\r\n");
    }

    private void responseBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

    private void processStatusLine(Status status) throws IOException {
        dos.writeBytes(String.format("%s %s \r\n", HttpVersion.USING_VERSION.get(), status.get()));
    }

    private void processHeader(HttpField httpHeader, String value) throws IOException {
        dos.writeBytes(httpHeader.get() + ": " + value + "\r\n");
    }

}
