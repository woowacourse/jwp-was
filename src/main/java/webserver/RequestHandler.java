package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.FileIoUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {
            final String requestPath = br.readLine().split(" ")[1];
            final int extensionIndex = requestPath.lastIndexOf(".");
            if (extensionIndex != -1 && requestPath.substring(extensionIndex + 1)
                    .matches("html|ico|css|js|eot|svg|woff|woff2|png|ttf")) {
                final byte[] body = FileIoUtils.loadFileFromClasspath("./static" + requestPath);
                response200Header(dos, body.length, requestPath.substring(extensionIndex + 1));
                responseBody(dos, body);
                return;
            }
            final byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + requestPath);
            response200Header(dos, body.length, requestPath.substring(extensionIndex + 1));
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String extension) {
        try {
            String type = "text/html";
            switch (extension) {
                case "html":
                    type = "text/html";
                    break;
                case "ico":
                    type = "image/x-icon";
                    break;
                case "css":
                    type = "text/css";
                    break;
                case "js":
                    type = "application/js";
                    break;
                case "png":
                    type = "image/png";
                    break;
                case "eot":
                    type = "application/x-font-eot";
                    break;
                case "svg":
                    type = "image/svg+xml";
                    break;
                case "woff":
                case "woff2":
                    type = "application/x-font-woff";
                    break;
                case "ttf":
                    type = "application/x-font-ttf";
            }
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + type + ";charset=utf-8\r\n");
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
