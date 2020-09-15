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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import http.HttpMethod;
import http.RequestBody;
import http.RequestHeaders;
import http.RequestLine;
import http.Uri;
import model.User;
import utils.FileIoUtils;
import utils.IOUtils;

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
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
             OutputStream outputStream = connection.getOutputStream();
             DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {

            List<String> requestLineAndHeader = IOUtils.readHeader(bufferedReader);
            logger.debug(requestLineAndHeader.toString());

            RequestLine requestLine = RequestLine.from(requestLineAndHeader.get(0));
            RequestHeaders requestHeaders = RequestHeaders.from(
                    requestLineAndHeader.subList(1, requestLineAndHeader.size()));
            Uri uri = requestLine.getUri();

            if ("/user/create".equals(uri.getPath()) && requestLine.equalsMethod(HttpMethod.POST)) {
                int contentLength = Integer.parseInt(requestHeaders.getHeader("Content-Length"));
                String body = IOUtils.readBody(bufferedReader, contentLength);
                RequestBody requestBody = RequestBody.from(body);
                String userId = requestBody.getValue("userId");
                String password = requestBody.getValue("password");
                String name = requestBody.getValue("name");
                String email = requestBody.getValue("email");
                User user = new User(userId, password, name, email);
                DataBase.addUser(user);
                response302Header(dataOutputStream, "/index.html");
            } else if (uri.getPath().endsWith(".html")) {
                byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + requestLine.getPath());
                response200Header(dataOutputStream, "text/html", body.length);
                responseBody(dataOutputStream, body);
            } else if (uri.getPath().endsWith(".js")) {
                byte[] body = FileIoUtils.loadFileFromClasspath("./static" + requestLine.getPath());
                response200Header(dataOutputStream, "text/javascript", body.length);
                responseBody(dataOutputStream, body);
            } else if (uri.getPath().endsWith(".css")) {
                byte[] body = FileIoUtils.loadFileFromClasspath("./static" + requestLine.getPath());
                response200Header(dataOutputStream, "text/css", body.length);
                responseBody(dataOutputStream, body);
            } else if (uri.getPath().endsWith(".svg")) {
                byte[] body = FileIoUtils.loadFileFromClasspath("./static" + requestLine.getPath());
                response200Header(dataOutputStream, "image/svg+xml", body.length);
                responseBody(dataOutputStream, body);
            } else if (uri.getPath().endsWith(".ttf")) {
                byte[] body = FileIoUtils.loadFileFromClasspath("./static" + requestLine.getPath());
                response200Header(dataOutputStream, "application/x-font-ttf", body.length);
                responseBody(dataOutputStream, body);
            } else if (uri.getPath().endsWith(".woff")) {
                byte[] body = FileIoUtils.loadFileFromClasspath("./static" + requestLine.getPath());
                response200Header(dataOutputStream, "application/x-font-woff", body.length);
                responseBody(dataOutputStream, body);
            } else if (uri.getPath().endsWith(".woff2")) {
                byte[] body = FileIoUtils.loadFileFromClasspath("./static" + requestLine.getPath());
                response200Header(dataOutputStream, "application/x-font-woff2", body.length);
                responseBody(dataOutputStream, body);
            } else if (uri.getPath().endsWith(".eot")) {
                byte[] body = FileIoUtils.loadFileFromClasspath("./static" + requestLine.getPath());
                response200Header(dataOutputStream, "application/vnd.ms-fontobject", body.length);
                responseBody(dataOutputStream, body);

            } else if (uri.getPath().endsWith(".png")) {
                byte[] body = FileIoUtils.loadFileFromClasspath("./static" + requestLine.getPath());
                response200Header(dataOutputStream, "image/png", body.length);
                responseBody(dataOutputStream, body);
            } else {
                byte[] body = "hello, world".getBytes();
                response200Header(dataOutputStream, "text/plain", body.length);
                responseBody(dataOutputStream, body);
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, String contentType, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type:  " + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Location: " + location + "\r\n");
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
