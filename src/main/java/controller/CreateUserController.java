package controller;

import db.DataBase;
import http.HttpStatusCode;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBody;
import http.response.HttpResponseHeader;
import http.response.HttpResponseStatusLine;
import model.User;

import java.io.IOException;
import java.net.URISyntaxException;

public class CreateUserController extends AbstractController {

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        save(httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email"));

        HttpResponseStatusLine httpResponseStatusLine;
        HttpResponseHeader HttpResponseHeader;
        HttpResponseBody httpResponseBody;
        try {
            httpResponseStatusLine = new HttpResponseStatusLine(httpRequest.getHttpVersion(), HttpStatusCode.of("302"));
            HttpResponseHeader = new HttpResponseHeader("Location: /index.html");
            httpResponseBody = new HttpResponseBody("");
        } catch (IOException e) {
            httpResponseStatusLine = new HttpResponseStatusLine(httpRequest.getHttpVersion(), HttpStatusCode.of("404"));
            HttpResponseHeader = new HttpResponseHeader("");
            httpResponseBody = new HttpResponseBody("");
        }

        httpResponse.setHttpResponseStatusLine(httpResponseStatusLine);
        httpResponse.setHttpResponseHeader(HttpResponseHeader);
        httpResponse.setHttpResponseBody(httpResponseBody);
    }

    private void save(String userId, String password, String name, String email) {
        if (DataBase.findUserById(userId) == null) {
            User user = new User(userId, password, name, email);
            DataBase.addUser(user);
            return;
        }

        throw new IllegalArgumentException();
    }
}
