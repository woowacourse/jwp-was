package controller;

import model.HttpRequest;
import model.HttpResponse;

public interface Controller {
    void service(HttpRequest request, HttpResponse httpResponse);
}
