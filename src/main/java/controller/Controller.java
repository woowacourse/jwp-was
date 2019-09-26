package controller;

import webserver.Request;
import webserver.RequestMapping;
import webserver.Response;

import java.util.Set;

public interface Controller {

    Response service(Request request);

    Set<RequestMapping> getMethodKeys();
}
