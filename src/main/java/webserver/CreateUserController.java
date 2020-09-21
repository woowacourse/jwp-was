package webserver;

import db.DataBase;
import java.io.IOException;
import java.net.URISyntaxException;
import model.User;

public class CreateUserController extends AbstractController {

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        DataBase.addUser(new User(
            httpRequest.getBodyParameter("userId"),
            httpRequest.getBodyParameter("password"),
            httpRequest.getBodyParameter("name"),
            httpRequest.getBodyParameter("email")
        ));
        httpResponse.sendRedirect("/index.html");
    }
}
