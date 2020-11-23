package controller;

import request.HttpRequest;
import response.HttpResponse;
import session.Session;

public interface Controller {

    HttpResponse service(HttpRequest httpRequest, Session session);
}
