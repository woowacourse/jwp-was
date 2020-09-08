package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import web.HttpMethod;
import web.HttpRequest;
import web.RequestBody;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Map;

;

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
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            HttpRequest httpRequest = new HttpRequest(br);
            String requestPath = httpRequest.getRequestPath();

            byte[] body = "".getBytes();
            if (requestPath.endsWith(".html") && HttpMethod.GET == httpRequest.getMethod()) {
                body = FileIoUtils.loadFileFromClasspath("./templates" + requestPath);
            } else if (requestPath.equals("/user/create") && HttpMethod.POST == httpRequest.getMethod()) {
                RequestBody requestBody = httpRequest.getRequestBody();
                Map<String, String> parsedBody = requestBody.parse();
                User user = new User(parsedBody.get("userId"),
                        parsedBody.get("password"),
                        parsedBody.get("name"),
                        parsedBody.get("email"));
                DataBase.addUser(user);
                body = user.toString().getBytes();
            }

            response200Header(dos, body.length);
            responseBody(dos, body);
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
