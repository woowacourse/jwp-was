package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.URIUtils;
import web.request.HttpRequest;
import web.request.RequestBody;
import web.request.RequestPath;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

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

            if (requestTarget.equals("/user/create")) {
                RequestBody requestBody = httpRequest.getRequestBody();
                User user = new User(requestBody.getParameters());
                DataBase.addUser(user);
                logger.debug("New User created! -> {}", user);

                response302Header(dos, "/index.html");

                byte[] body = user.toString().getBytes();
                responseBody(dos, body);

                return;
            }
            String filePath = URIUtils.getFilePath(requestTarget);
            byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
            response200Header(dos, httpRequest.getContentType(), body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
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

    private void response302Header(DataOutputStream dos, String relocateURI) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: " + relocateURI + " \r\n");
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
