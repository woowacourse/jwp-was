package was.controller;

import db.DataBase;
import webserver.http.request.HttpRequest;
import webserver.http.request.QueryStringParams;
import webserver.http.response.HttpResponse;
import was.model.User;

public class CreateUserController extends AbstractController {

    @Override
    void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        QueryStringParams httpsParameters = httpRequest.getQueryStringParams();

        User user = new User(
                httpsParameters.get("userId"),
                httpsParameters.get("password"),
                httpsParameters.get("name"),
                httpsParameters.get("email")
        );

        DataBase.addUser(user);

        httpResponse.redirect("/index.html");
    }
}
