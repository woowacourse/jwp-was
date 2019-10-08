package controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

public interface Controller {
    HttpResponse handle(HttpRequest req);
}