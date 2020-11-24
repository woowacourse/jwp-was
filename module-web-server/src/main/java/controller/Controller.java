package controller;

import model.request.HttpRequest;
import model.response.HttpResponse;

public interface Controller {

    HttpResponse service(HttpRequest request);
}
