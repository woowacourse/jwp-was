package webserver.pageprovider;

import http.request.HttpRequestAccessor;
import http.response.HttpResponseAccessor;
import model.User;
import webserver.page.HandlebarsPage;
import webserver.page.Page;
import webserver.page.RedirectPage;

import java.util.Optional;

public class UserProfilePageProvider implements PageProvider {
    @Override
    public Page provide(HttpRequestAccessor request, HttpResponseAccessor response) {
        return retrieveLoggedInUser(request)
                .map(user -> (Page) HandlebarsPage.locationWithObj("user/profile", user))
                .orElse(RedirectPage.location("/user/login.html"));
    }

    private Optional<User> retrieveLoggedInUser(HttpRequestAccessor request) {
        return request.getSession(false)
                .map(session -> (User) session.getAttribute("user"));
    }
}
