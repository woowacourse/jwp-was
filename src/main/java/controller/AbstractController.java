package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import view.View;

public abstract class AbstractController implements Controller {
    public abstract View doGet(HttpRequest httpRequest, HttpResponse httpResponse);
    public abstract View doPost(HttpRequest httpRequest, HttpResponse httpResponse);
}
