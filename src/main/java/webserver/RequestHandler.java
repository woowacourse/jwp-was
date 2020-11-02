package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.UserController;
import lombok.AllArgsConstructor;
import utils.FileIoUtils;
import webserver.protocol.HttpRequest;
import webserver.protocol.HttpRequestParser;

@AllArgsConstructor
public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (
            final InputStream in = connection.getInputStream();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            final OutputStream out = connection.getOutputStream();
            final DataOutputStream dos = new DataOutputStream(out)
        ) {
            final HttpRequest httpRequest = HttpRequestParser.parse(reader);

            if (httpRequest.hasContentLength()) {
                final String path = httpRequest.getPath();
                if ("/user/create".equals(path)) {
                    UserController.create(httpRequest.getBody().getContents());
                    response302Header(dos);
                }
            } else {
                if (httpRequest.isAcceptCSS()) {
                    final byte[] responseBody = FileIoUtils.loadFileFromClasspath(
                        FileIoUtils.STATIC_PATH + httpRequest.getPath());
                    response200CSSHeader(dos, responseBody.length);
                    responseBody(dos, responseBody);
                } else {
                    final byte[] responseBody = FileIoUtils.loadFileFromClasspath(
                        FileIoUtils.TEMPLATES_PATH + httpRequest.getPath());
                    response200Header(dos, responseBody.length);
                    responseBody(dos, responseBody);
                }
            }

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200CSSHeader(final DataOutputStream dos, final int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK " + System.lineSeparator());
            dos.writeBytes("Content-Type: text/css; charset=\"utf-8\"" + System.lineSeparator());
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + System.lineSeparator());
            dos.writeBytes(System.lineSeparator());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(final DataOutputStream dos, final int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK " + System.lineSeparator());
            dos.writeBytes("Content-Type: text/html;charset=utf-8" + System.lineSeparator());
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + System.lineSeparator());
            dos.writeBytes(System.lineSeparator());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(final DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found " + System.lineSeparator());
            dos.writeBytes("Location: /index.html " + System.lineSeparator());
            dos.writeBytes(System.lineSeparator());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(final DataOutputStream dos, final byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
