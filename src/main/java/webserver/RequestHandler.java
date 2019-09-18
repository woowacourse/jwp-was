package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.domain.Request;
import webserver.view.NetworkInput;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String TEMPLATES_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final Request request = new Request(new NetworkInput(in));
            final byte[] body = readBody(request);
            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            logger.error(e.getMessage());
        }
    }

    private String makeFilePath(final Request requestHeader, final String prefix) {
        final String requestPath = requestHeader.getPath();
        final String pathEnd = (requestPath.endsWith("/") || "".equals(requestPath)) ? "index.html" : "";
        return prefix + requestPath + pathEnd;
    }

    private byte[] readBody(final Request requestHeader) throws IOException, URISyntaxException, NullPointerException {
        try {
            return FileIoUtils.loadFileFromClasspath(makeFilePath(requestHeader, STATIC_PATH));
        } catch (IOException | URISyntaxException | NullPointerException e) {
            return FileIoUtils.loadFileFromClasspath(makeFilePath(requestHeader, TEMPLATES_PATH));
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

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
