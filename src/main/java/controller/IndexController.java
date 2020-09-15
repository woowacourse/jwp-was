package controller;

import annotation.Controller;
import annotation.RequestMapping;
import http.request.HttpMethod;

@Controller
public class IndexController {
    @RequestMapping
    public void index() {

    }

    @RequestMapping(path = "/not-found", method = HttpMethod.POST)
    public void notFound() {
    }
}
