package was.controller;

import db.DataBase;
import was.model.User;
import webserver.http.request.HttpRequest;
import webserver.http.request.QueryStringParams;
import webserver.http.response.HttpResponse;

public class CreateUserController extends AbstractController {
    private CreateUserController() {}

    public static CreateUserController getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final CreateUserController INSTANCE = new CreateUserController();
    }

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
