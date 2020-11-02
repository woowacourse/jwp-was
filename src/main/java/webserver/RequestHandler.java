package webserver;

import controller.UserController;
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

import lombok.AllArgsConstructor;
import utils.FileIoUtils;
import utils.IOUtils;
import webserver.protocol.RequestBody;
import webserver.protocol.RequestBodyParser;
import webserver.protocol.RequestHeader;
import webserver.protocol.RequestHeaderParser;

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
            final RequestHeader requestHeader = RequestHeaderParser.parse(IOUtils.readHeaderData(reader));

            if (requestHeader.hasContentLength()) {
                final String contentLength = requestHeader.getHeaders().get("Content-Length");
                final String bodyData = IOUtils.readBodyData(reader, Integer.parseInt(contentLength));
                final RequestBody requestBody = RequestBodyParser.parse(bodyData);

                final String path = requestHeader.getPath();
                if ("/user/create".equals(path)) {
                    UserController.create(requestBody.getContents());
                    response302Header(dos);
                }
            } else {
                if (requestHeader.isAcceptCSS()) {
                    final byte[] responseBody = FileIoUtils.loadFileFromClasspath(
                        FileIoUtils.STATIC_PATH + requestHeader.getPath());
                    response200CSSHeader(dos, responseBody.length);
                    responseBody(dos, responseBody);
                } else {
                    final byte[] responseBody = FileIoUtils.loadFileFromClasspath(
                        FileIoUtils.TEMPLATES_PATH + requestHeader.getPath());
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
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css; charset=\"utf-8\"\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(final DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: /index.html \r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
