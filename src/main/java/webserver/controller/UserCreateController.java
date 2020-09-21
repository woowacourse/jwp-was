package webserver.controller;

import java.io.IOException;

import http.HttpStatus;
import http.QueryParams;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.service.UserService;

public class UserCreateController extends Controller {
    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        next(request, response);
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws IOException {
        QueryParams queryParams = QueryParams.parse(request.getBody());
        UserService.create(queryParams.get("userId"),
            queryParams.get("password"),
            queryParams.get("name"),
            queryParams.get("email"));
        response.status(HttpStatus.CREATED).end(null);
    }
}
