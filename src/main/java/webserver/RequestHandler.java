package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream inputStream = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {
            RequestHeader requestHeader = RequestHeader.of(inputStream);

            String path;
            String uri = requestHeader.getValue("Uri");
            if (requestHeader.getValue("Accept").contains("text/html")
                    || uri.contains("favicon")) {
                path = "./templates" + uri;
            } else {
                path = "./static" + uri;
            }

            DataOutputStream dos = new DataOutputStream(out);
            byte[] responseBody = FileIoUtils.loadFileFromClasspath(path);

            String contentType = "text/html; charset=utf-8";
            if (uri.contains(".js")) {
                contentType = "text/javascript; charset=utf-8";
            }
            if (uri.contains(".css")) {
                contentType = "text/css; charset=utf-8";
            }
            if (uri.contains(".ico")) {
                contentType = "image/x-icon";
            }
            response200Header(dos, responseBody.length, contentType);
            responseBody(dos, responseBody);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes(String.format("Content-Type: %s\r\n", contentType));
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
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
