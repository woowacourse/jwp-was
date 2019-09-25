package dev.luffy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.luffy.annotation.Controller;
import dev.luffy.annotation.RequestMapping;
import dev.luffy.http.request.HttpRequest;
import dev.luffy.http.response.HttpResponse;

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/index.html")
    public static void index(HttpRequest request, HttpResponse response) {

        logger.debug("request : {} & response : {}", request, response);

        response.ok(request);
    }
}
