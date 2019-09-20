package controller;

import http.HttpRequest;
import http.HttpResponse;
import view.View;

public abstract class AbstractController implements Controller {
    public abstract View doGet(HttpRequest httpRequest, HttpResponse httpResponse);
    public abstract View doPost(HttpRequest httpRequest, HttpResponse httpResponse);
}
