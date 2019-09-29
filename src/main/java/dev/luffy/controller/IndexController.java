package dev.luffy.controller;

import dev.luffy.http.request.HttpRequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.luffy.annotation.Controller;
import dev.luffy.annotation.RequestMapping;
import dev.luffy.http.request.HttpRequest;
import dev.luffy.http.response.HttpResponse;

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(
            path = "/index.html",
            method = HttpRequestMethod.GET
    )
    public static void index(HttpRequest request, HttpResponse response) {

        logger.debug("request : {}", request);

        response.ok(request);
    }
}
