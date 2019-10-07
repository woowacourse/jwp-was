package webserver.pageprovider;

import db.DataBase;
import http.request.HttpRequestAccessor;
import http.response.HttpResponseAccessor;
import model.User;
import webserver.page.HandlebarsPage;
import webserver.page.Page;

import java.util.ArrayList;
import java.util.List;

public class UserListPageProvider implements PageProvider {
    @Override
    public Page provide(HttpRequestAccessor request, HttpResponseAccessor response) {
        List<User> users = new ArrayList<>(DataBase.findAll());

        return HandlebarsPage.locationWithObj("user/list", users);
    }
}
