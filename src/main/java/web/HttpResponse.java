package web;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.FileIoUtils;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream dos;
    private Map<String, String> headers = new HashMap<>();

    public HttpResponse(OutputStream output) {
        dos = new DataOutputStream(output);
    }

    public void forward(String path) {
        try {
            StaticFile staticFile = StaticFile.of(path);
            byte[] body = FileIoUtils.loadFileFromClasspath(staticFile.getPrefix() + path);

            headers = StaticFile.writeContentType(headers, path);
            headers.put("Content-Length", body.length + "");
            response200Header(body.length);
            responseBody(body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    public void forwardBody(String body) {
        byte[] contents = body.getBytes();
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", contents.length + "");
        response200Header(contents.length);
        responseBody(contents);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void response405Header() {
        try {
            dos.writeBytes("HTTP/1.1 405 Method Not Allowed" + System.lineSeparator());
            dos.writeBytes(System.lineSeparator());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response404Header() {
        try {
            dos.writeBytes("HTTP/1.1 404 Not Found" + System.lineSeparator());
            dos.writeBytes(System.lineSeparator());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendRedirect(String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found" + System.lineSeparator());
            processHeaders();
            dos.writeBytes("Location: " + redirectUrl + System.lineSeparator());
            dos.writeBytes(System.lineSeparator());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response200Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK" + System.lineSeparator());
            processHeaders();
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + System.lineSeparator());
            dos.writeBytes(System.lineSeparator());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void processHeaders() {
        try {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                dos.writeBytes(key + ": " + headers.get(key) + System.lineSeparator());
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
