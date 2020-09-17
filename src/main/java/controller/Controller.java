package controller;

import request.HttpRequest;
import response.HttpResponse;

public interface Controller {

    HttpResponse service(HttpRequest httpRequest);
}
