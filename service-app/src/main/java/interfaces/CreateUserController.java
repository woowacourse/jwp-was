package interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import model.UserDto;
import web.controller.AbstractController;
import web.http.HttpRequest;
import web.http.HttpRequestParams;
import web.http.HttpResponse;

public class CreateUserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        HttpRequestParams requestParams = request.getRequestBody();
        UserDto userDto = new UserDto(
            requestParams.getParameter("userId"),
            requestParams.getParameter("password"),
            requestParams.getParameter("name"),
            requestParams.getParameter("email"));

        User user = userDto.toUser();
        logger.info("user : {}", user);
        DataBase.addUser(user);
        response.sendRedirect("/index.html");
    }
}
