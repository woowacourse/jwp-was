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

import db.DataBase;
import model.User;
import utils.FileIoUtils;
import utils.RequestUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            String requestHeader = RequestUtils.extractHeader(bufferedReader);
            logRequest(requestHeader);
            String wholeUrl = RequestUtils.extractWholeUrl(requestHeader);
            String path = RequestUtils.extractPath(wholeUrl);

            DataOutputStream dos = new DataOutputStream(out);

            String method = RequestUtils.extractMethod(requestHeader);
            if ("/user/create".equals(path) && "POST".equals(method)) {
                String contentLength = RequestUtils.extractHeaderValue(requestHeader, "Content-Length");
                String body = RequestUtils.extractBody(bufferedReader, contentLength);
                Map<String, String> userInfo = RequestUtils.extractBodyParams(body);
                User user = new User(
                    userInfo.get("userId"), userInfo.get("password"), userInfo.get("name"), userInfo.get("email"));
                DataBase.addUser(user);
                response302Header(dos);
            } else {
                String localPath = parseToLocalPath(path);
                byte[] body = FileIoUtils.loadFileFromClasspath(localPath);
                response200Header(dos, body.length, parseContentType(path));
                responseBody(dos, body);
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void logRequest(String request) {
        logger.debug(
            System.lineSeparator() + "----- 요청 시작 -----"
                + System.lineSeparator() + request
                + "----- 요청 끝 -----");
    }

    private String parseToLocalPath(String path) {
        if (path.endsWith(".html") || path.endsWith(".ico")) {
            return "./templates" + path;
        }
        return "./static" + path;
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: /index.html \r\n");
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

    private String parseContentType(String path) {
        if (path.endsWith(".css")) {
            return "text/css";
        }
        return "text/html;charset=utf-8";
    }
}
