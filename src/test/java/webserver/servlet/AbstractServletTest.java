package webserver.servlet;

import model.User;
import webserver.http.request.*;
import webserver.http.response.HttpResponse;
import webserver.resolver.Resolver;

import java.io.IOException;
import java.util.HashMap;

public abstract class AbstractServletTest {
    HttpResponse httpResponse;
    AbstractRequestServlet httpServlet;
    Resolver resolver;
    User dummyUser = new User("id", "password", "name", "mail@mail");

    protected HttpRequest getCommonGetRequest(String uri) throws IOException {
        return new HttpRequest(new RequestLine("GET", uri, "HTTP/1.1")
                , new RequestHeader(new HashMap<>()),
                new RequestBody(null),
                new Cookie(null));
    }

    protected HttpRequest getCreateUserPostRequest() throws IOException {
        return new HttpRequest(new RequestLine("POST", "/user/create", "HTTP/1.1")
                , new RequestHeader(new HashMap<>()),
                new RequestBody(createUserBody()),
                new Cookie(null));
    }

    protected String createUserBody() {
        return "userId=" + dummyUser.getUserId() +
                "&password=" + dummyUser.getPassword() +
                "&name=" + dummyUser.getName() +
                "&email=" + dummyUser.getEmail();
    }
}
