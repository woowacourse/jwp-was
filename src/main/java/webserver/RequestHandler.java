package webserver;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.request.HttpRequest;
import webserver.request.RequestMethod;
import webserver.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

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

            HttpRequest request = new HttpRequest(in);
            String filePath = request.getFilePath();


            byte[] body = null;
            int statusCode = 200;
            Map<String, Object> header = new HashMap<>();

            if (request.getMethod() == RequestMethod.GET) {
                body = FileIoUtils.loadFileFromClasspath(filePath);
                header.put("lengthOfBodyContent", body.length);
                header.put("Content-Type", FileIoUtils.loadMIMEFromClasspath(filePath));
            }
            if (request.getMethod() == RequestMethod.POST && request.getAbsPath().equals("/user/create")) {
                User user = new User(request.getBody("userId"), request.getBody("password"), request.getBody("name"), request.getBody("email"));
                logger.debug(">>> User : {}", user);
                statusCode = 302;
                header.put("location", "/index.html");
            }

            DataOutputStream dos = new DataOutputStream(out);
            HttpResponse httpResponse = new HttpResponse(dos, statusCode, header, body);
            httpResponse.render();

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }


}
