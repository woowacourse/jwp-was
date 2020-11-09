package webserver;

import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class UserServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        final String userId = request.getParameter("userId");
        final String password = request.getParameter("password");
        final String name = request.getParameter("name");
        final String email = request.getParameter("email");
        final User user = new User(userId, password, name, email);
        if (DataBase.findUserById(userId) != null) {
            throw new IllegalArgumentException();
        }
        DataBase.addUser(user);
        System.out.println("회원가입 GET 요청을 처리한다." + user);
        // response200Header(dos, body.length, matcher.getMimeType());
        // responseBody(dos, body);
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        System.out.println("회원가입 POST 요청을 처리한다.");
        final String userId = request.getParameter("userId");
        final String password = request.getParameter("password");
        final String name = request.getParameter("name");
        final String email = request.getParameter("email");
        final User user = new User(userId, password, name, email);
        if (DataBase.findUserById(userId) != null) {
            throw new IllegalArgumentException();
        }
        DataBase.addUser(user);
        System.out.println("회원가입 POST 요청을 처리한다." + user);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String mimeType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + mimeType + ";charset=utf-8\r\n");
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
