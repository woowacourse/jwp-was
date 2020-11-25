package webserver.controller;

import http.QueryParams;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.IOException;
import webserver.service.UserService;

public class UserCreateController extends Controller {
    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        next(request, response);
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws IOException {
        QueryParams queryParams = QueryParams.parse(request.getBody());
        UserService.create(queryParams.getFirst("userId"),
            queryParams.getFirst("password"),
            queryParams.getFirst("name"),
            queryParams.getFirst("email"));
        response.sendRedirect("/index.html");
    }
}
