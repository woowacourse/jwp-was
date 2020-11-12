package webserver;

import static com.google.common.net.HttpHeaders.*;
import static webserver.http.response.HttpStatus.*;

import db.DataBase;
import model.User;
import webserver.exception.DuplicatedUserIdException;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        createUser(request, response);
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        createUser(request, response);
    }

    private void createUser(HttpRequest request, HttpResponse response) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        if (DataBase.findUserById(userId) != null) {
            throw new DuplicatedUserIdException(userId);
        }
        DataBase.addUser(new User(userId, password, name, email));
        response.changeHttpStatus(FOUND);
        response.addHeader(LOCATION, "/index.html");
    }
}
