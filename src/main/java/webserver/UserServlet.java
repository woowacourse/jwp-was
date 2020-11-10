package webserver;

import static com.google.common.net.HttpHeaders.*;
import static webserver.http.response.HttpStatus.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import webserver.exception.DuplicatedUserIdException;
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
            throw new DuplicatedUserIdException(userId);
        }
        DataBase.addUser(user);
        response.changeHttpStatus(FOUND);
        response.addHeader(LOCATION, "/index.html");
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        final String userId = request.getParameter("userId");
        final String password = request.getParameter("password");
        final String name = request.getParameter("name");
        final String email = request.getParameter("email");
        final User user = new User(userId, password, name, email);
        if (DataBase.findUserById(userId) != null) {
            throw new DuplicatedUserIdException(userId);
        }
        DataBase.addUser(user);
        System.out.println("회원가입 POST 요청을 처리한다." + user);
        response.changeHttpStatus(FOUND);
        response.addHeader(LOCATION, "/index.html");
    }
}
