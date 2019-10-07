package webserver.pageprovider;

import db.DataBase;
import http.request.HttpRequestAccessor;
import http.response.HttpResponseAccessor;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.page.Page;
import webserver.page.RedirectPage;

public class UserSignUpPageProvider implements PageProvider {
    private static final Logger log = LoggerFactory.getLogger(UserSignUpPageProvider.class);

    @Override
    public Page provide(HttpRequestAccessor request, HttpResponseAccessor response) {
        User user = createUser(request);

        log.debug("user: {}", user);
        DataBase.addUser(user);

        return RedirectPage.location("/index.html");
    }

    private User createUser(HttpRequestAccessor request) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        return new User(userId, password, name, email);
    }
}
