package http.application.controller;

import http.application.Service;
import http.application.service.UserListService;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class UserListController extends AbstractController {

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        Service userListService = new UserListService();
        userListService.execute(httpRequest, httpResponse);
    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

    }
}
