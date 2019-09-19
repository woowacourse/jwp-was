package webserver;

import controller.HomeController;
import controller.UserController;
import http.request.HttpRequestFactory;
import http.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String TEMPLATE_PATH = "../resources/templates";
    private static final String STATIC_PATH = "../resources/static";

    private Socket connection;
    private UserController userController;
    private HomeController homeController;

    public RequestHandler(Socket connection) {
        this.connection = connection;
        homeController = HomeController.getInstance();
        userController = UserController.getInstance();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            Request request = HttpRequestFactory.getRequest(br);

            request.getParams();
            if(request.getRequestPath().getPath().equals(TEMPLATE_PATH + "/") || request.getRequestPath().getPath().equals(TEMPLATE_PATH+"/index.html")) {
                homeController.home(request);
            }

//            String path = httpRequest.getRequestPath().getPath();
//
//            if (path.contains("?")) {
//                logger.debug("handler : {}", path.contains(""));
//                userService.createUser(httpRequest.getRequestPath().getParameters());
//            }
//
//            DataOutputStream dos = new DataOutputStream(out);
//            byte[] body = FileIoUtils.loadFileFromClasspath(path);
//
//            response200Header(dos, body.length);
//            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } //catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
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
