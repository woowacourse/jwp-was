package webserver;

import controller.UserController;
import dto.UserCreateRequest;
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
import request.HttpRequest;
import request.Method;
import resource.Resource;
import resource.ResourcesHandler;
import utils.IOUtils;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private ResourcesHandler resourcesHandler = new ResourcesHandler();
    private UserController userController = new UserController();

    RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(), connection.getPort());

        try (
            InputStream in = connection.getInputStream();
            OutputStream out = connection.getOutputStream();
            BufferedReader br = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8));
        ) {
            HttpRequest httpRequest = readRequest(br);

            if (httpRequest.isMethod(Method.POST) && httpRequest.isUriPath("/user/create")) {
                Map<String, String> queryData = httpRequest.getFormDataFromBody();
                UserCreateRequest userCreateRequest = new UserCreateRequest(
                    queryData.get("userId"),
                    queryData.get("password"),
                    queryData.get("name"),
                    queryData.get("email")
                );
                userController.createUser(userCreateRequest);

                DataOutputStream dos = new DataOutputStream(out);
                response302Header(dos, "/");
                return;
            }
            Resource resourceForResponse =
                resourcesHandler.convertUriToResource(httpRequest.getUriPath());
            byte[] body = resourceForResponse.getResource();
            String contentType = resourceForResponse.getContentType();

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length, contentType);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpRequest readRequest(BufferedReader br) throws IOException {
        String requestHeader = IOUtils.readDataBeforeEmptyLine(br);
        String requestBody = "";

        if (HttpRequest.isExistRequestHeader(requestHeader, "Content-Length")) {
            int contentLength = Integer.parseInt(HttpRequest.findHeaderValue(
                requestHeader, "Content-Length"));
            requestBody = IOUtils.readData(br, contentLength);
        }
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());
        logger.debug("Receive HttpRequest\n{}\n{}", requestHeader, requestBody);

        return new HttpRequest(requestHeader, requestBody);
    }

    private void response200Header(DataOutputStream dos,
            int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response201Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 201 OK \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
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
