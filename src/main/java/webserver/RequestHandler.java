package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.HttpStartLine;
import model.User;
import utils.FileIoUtils;

public class RequestHandler implements Runnable {
    private static final String TEMPLATES_PATH = "./templates";
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream inputStream = connection.getInputStream(); OutputStream outputStream = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String firstLine = bufferedReader.readLine();
            if (firstLine == null) {
                return;
            }

            HttpStartLine httpStartLine = new HttpStartLine(firstLine);
            String path = httpStartLine.getPath();
            if (httpStartLine.isSamePath("/user/create")) {
                Map<String, String> parameters = httpStartLine.getParameters();

                User user = new User(
                    parameters.get("userId"),
                    parameters.get("password"),
                    parameters.get("name"),
                    parameters.get("email")
                );

                logger.debug("Saved User: {}", user);
            }

            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            byte[] body = FileIoUtils.loadFileFromClasspath(TEMPLATES_PATH + path);
            response200Header(dataOutputStream, body.length);
            responseBody(dataOutputStream, body);

        } catch (IOException | URISyntaxException e) {
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

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
