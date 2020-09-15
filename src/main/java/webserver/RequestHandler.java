package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

import db.DataBase;
import exception.InvalidHttpRequestException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import web.request.HttpRequest;
import web.request.RequestPath;

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

            HttpRequest httpRequest = new HttpRequest(bufferedReader);

            RequestPath requestPath = httpRequest.getRequestPath();
            String requestTarget = requestPath.getTarget();
            DataOutputStream dos = new DataOutputStream(out);

            if(requestTarget.equals("/user/create")) {
                User user = new User(requestPath.getPathParameters());
                DataBase.addUser(user);

                //// TODO: 2020/09/14 이후 302 리스폰스에 대한 액션 구현시까지는...여기를 비운다.
                return;
            }
            byte[] body = FileIoUtils.loadFileFromClasspath(requestTarget);
            response200Header(dos, httpRequest.getContentType(), body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException | InvalidHttpRequestException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, String contentType, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
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
