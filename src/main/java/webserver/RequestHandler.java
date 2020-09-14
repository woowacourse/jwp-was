package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import utils.FileIoUtils;
import web.RequestBody;
import web.RequestHeader;
import web.RequestLine;
import web.StaticFile;

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
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            RequestLine requestLine = new RequestLine(bufferedReader);
            RequestHeader requestHeader = new RequestHeader(bufferedReader);
            RequestBody requestBody = null;
            if (requestLine.getMethod().equals("POST")) {
                requestBody = new RequestBody(bufferedReader, requestHeader.getContentLength());
            }

            DataOutputStream dos = new DataOutputStream(out);
            String requestPath = requestLine.getPath();
            byte[] body;
            if (requestPath.endsWith("/user/create") && "POST".equals(requestLine.getMethod())) {
                Map<String, String> parsedBody = requestBody.parseBody();
                String userId = parsedBody.get("userId");
                String password = parsedBody.get("password");
                String name = parsedBody.get("name");
                String email = parsedBody.get("email");

                User user = new User(userId, password, name, email);
                DataBase.addUser(user);
                body = user.toString().getBytes();
                response302Header(dos, "/index.html");
                responseBody(dos, body);
            } else if ("GET".equals(requestLine.getMethod())) {
                StaticFile staticFile = StaticFile.findStaticFile(requestLine.getPath());
                body = FileIoUtils.loadFileFromClasspath(staticFile.getResourcePath() + requestPath);
                response200Header(dos, body.length, staticFile.getContentType());
                responseBody(dos, body);
            }

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

    private void response302Header(DataOutputStream dos, String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + redirectUrl + "\r\n");
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
