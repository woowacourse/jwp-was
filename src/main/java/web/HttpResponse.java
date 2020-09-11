package web;

import static web.RequestHeader.*;
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
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream dos;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public void process(HttpRequest httpRequest) {
        RequestHeader requestHeader = httpRequest.getRequestHeader();
        if (requestHeader.isRootPath() || requestHeader.isStaticFile()) {
            processHtml(httpRequest);
        } else {
            processApi(httpRequest);
        }
    }

    private void processHtml(HttpRequest httpRequest) {
        String HTML_PATH_PREFIX = "./templates";
        byte[] content = null;
        try {
            content = FileIoUtils.loadFileFromClasspath(
                    HTML_PATH_PREFIX + httpRequest.getRequestUri());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        if (content != null) {
            String response = response200Header(content);
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
            logger.debug("USER 회원가입 성공 : " + user.toString());
            String response = response302Header("localhost:8080" + INDEX_HTML);
            toDataOutputStream(response);
            responseBody(new byte[0]);
        }
    }

    private String response200Header(byte[] content) {
        return "HTTP/1.1 200 OK" + NEW_LINE
                + "Content-Type: text/html;charset=utf-8" + NEW_LINE
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
