package webserver.servlet;

import db.DataBase;
import helper.StorageHelper;
import model.User;
import webserver.http.request.*;
import webserver.http.response.HttpResponse;
import webserver.resolver.Resolver;
import webserver.session.SessionStorage;

import java.util.HashMap;

public abstract class AbstractServletTest {
    HttpResponse httpResponse;
    AbstractRequestServlet httpServlet;
    Resolver resolver;
    User dummyUser = new User("id", "password", "name", "mail@mail");
    String dummySessionID = "dummySessionID";

    protected HttpRequest getCommonGetRequest(String uri) {
        return new HttpRequest(new RequestLine("GET", uri, "HTTP/1.1")
                , new RequestHeader(new HashMap<>()),
                new RequestBody(null),
                new Cookie(null));
    }

    protected HttpRequest getCreateUserPostRequest() {
        return new HttpRequest(new RequestLine("POST", "/user/create", "HTTP/1.1")
                , new RequestHeader(new HashMap<>()),
                new RequestBody(createUserBody()),
                new Cookie(null));
    }

    protected HttpRequest getLoginPostRequest() {
        return new HttpRequest(new RequestLine("POST", "/user/login", "HTTP/1.1")
                , new RequestHeader(new HashMap<>()),
                new RequestBody(loginBody()),
                new Cookie(null));
    }

    protected HttpRequest getUserListRequest(String sessionId) {
        return new HttpRequest(new RequestLine("POST", "/user/list", "HTTP/1.1")
                , new RequestHeader(new HashMap<>()),
                new RequestBody(loginBody()),
                new Cookie(Cookie.SESSION_ID_KEY + "=" + sessionId));
    }

    protected String createUserBody() {
        return "userId=" + dummyUser.getUserId() +
                "&password=" + dummyUser.getPassword() +
                "&name=" + dummyUser.getName() +
                "&email=" + dummyUser.getEmail();
    }

    protected String loginBody() {
        return "userId=" + dummyUser.getUserId() +
                "&password=" + dummyUser.getPassword();
    }


    protected void signUpAndLogin(User user, String sessionId) {
        signUp(user);
        login(user, sessionId);
    }

    protected void signUp(User user) {
        DataBase.addUser(user);
    }

    protected void login(User user, String sessionId) {
        SessionStorage.create(StorageHelper.idGenerator(sessionId));
        SessionStorage.get(sessionId).setAttribute("user", user);
    }

    protected void logout(String sessionId){
        SessionStorage.get(sessionId).invalidate();
    }
}
