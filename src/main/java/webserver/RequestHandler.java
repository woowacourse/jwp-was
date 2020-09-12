package webserver;

import db.DataBase;
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
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.httpmessages.request.Method;
import webserver.httpmessages.request.Request;
import webserver.resource.Resource;
import webserver.resource.ResourcesHandler;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private ResourcesHandler resourcesHandler = new ResourcesHandler();

    RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (
            InputStream in = connection.getInputStream();
            OutputStream out = connection.getOutputStream()
        ) {
            BufferedReader br = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8));

            Request httpRequest = readRequest(br);

            if (httpRequest.isMethod(Method.POST) && httpRequest.isUriPath("/user/create")) {
                Map<String, String> queryData = httpRequest.getFormDataFromBody();
                User user = new User(
                    queryData.get("userId"),
                    queryData.get("password"),
                    queryData.get("name"),
                    queryData.get("email")
                );
                DataBase.addUser(user);

                DataOutputStream dos = new DataOutputStream(out);
                response201Header(dos, "/user/" + user.getUserId());

                return;
            }
            Resource resourceForResponse =
                resourcesHandler.convertUriToResource(httpRequest.getUri());
            byte[] body = resourceForResponse.getResource();
            String contentType = resourceForResponse.getContentType();

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length, contentType);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private Request readRequest(BufferedReader br) throws IOException {
        String requestHeader = IOUtils.readDataBeforeEmptyLine(br);
        String requestBody = "";

        if (Request.isExistRequestHeader(requestHeader, "Content-Length")) {
            int contentLength = Integer.parseInt(Request.findHeaderValue(
                requestHeader, "Content-Length"));
            requestBody = IOUtils.readData(br, contentLength);
        }
        System.out.println("## 요청 들어옴! ##");
        System.out.println(requestHeader);
        System.out.println();
        System.out.println(requestBody);
        System.out.println("##################");

        return new Request(requestHeader, requestBody);
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

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
