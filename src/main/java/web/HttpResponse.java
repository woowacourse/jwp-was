package web;

import static web.RequestUri.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import utils.FileIoUtils;

public class HttpResponse {
    public static final String NEW_LINE = System.lineSeparator();

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream dos;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public void process(HttpRequest httpRequest) {
        if (httpRequest.isStaticFile()) {
            processFile(httpRequest);
        } else {
            processApi(httpRequest);
        }
    }

    private void processFile(HttpRequest httpRequest) {
        RequestUri requestUri = httpRequest.getRequestUri();
        String resourcePath = requestUri.findPath() + requestUri.getUri();
        byte[] content = null;
        try {
            content = FileIoUtils.loadFileFromClasspath(
                    resourcePath);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        if (content != null) {
            String response = response200Header(requestUri.findContentType(), content);
            toDataOutputStream(response);
            responseBody(content);
        }
    }

    private void processApi(HttpRequest httpRequest) {
        if (httpRequest.isPost()) {
            Map<String, String> body = httpRequest.getRequestBody().getFormData();
            User user = new User(body.get("userId"), body.get("password"), body.get("name"),
                    body.get("email"));
            DataBase.addUser(user);
            String response = response302Header("http://localhost:8080" + INDEX_HTML);
            toDataOutputStream(response);
            responseBody(new byte[0]);
        }
    }

    private String response200Header(String contentType, byte[] content) {
        return "HTTP/1.1 200 OK" + NEW_LINE
                + "Content-Type: " + contentType + NEW_LINE
                + "Content-Length: " + content.length + NEW_LINE
                + NEW_LINE;
    }

    private String response302Header(String redirectUrl) {
        return "HTTP/1.1 302 Found" + NEW_LINE
                + "Location: " + redirectUrl + NEW_LINE
                + NEW_LINE;
    }

    private void toDataOutputStream(String response) {
        try {
            dos.writeBytes(response);
            logger.debug(response);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void responseBody(byte[] content) {
        try {
            dos.write(content, 0, content.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
