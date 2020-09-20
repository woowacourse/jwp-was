package slipp.web;

import java.util.Objects;

import slipp.db.DataBase;
import slipp.model.User;
import webserver.controller.AbstractController;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestParams;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

public class UserController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestParams requestParams = RequestParams.from(httpRequest.getHttpBody().getBody());

        if (Objects.isNull(requestParams.get("userId")) || Objects.isNull(requestParams.get("password"))) {
            httpResponse.sendError(HttpStatus.BAD_REQUEST, "id 또는 비밀번호가 존재하지 않습니다.");
            return;
        }

        DataBase.addUser(
            new User(requestParams.getOneParameterValue("userId"), requestParams.getOneParameterValue("password"),
                requestParams.getOneParameterValue("name"), requestParams.getOneParameterValue("email")));

        httpResponse.sendRedirect("/index.html");
    }
}
