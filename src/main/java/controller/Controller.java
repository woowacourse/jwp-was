package controller;

import webserver.Request;
import webserver.Response;

public interface Controller {

    Response service(Request request);

    String getPath();
}
