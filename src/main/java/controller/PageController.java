package controller;

import webserver.Request;

public class PageController {

    public static String getPagePath(Request request) {
        return "./templates" + request.getResource();
    }
}
