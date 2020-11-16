package user.service.controller;

import static http.was.http.ContentType.*;

import java.io.IOException;
import java.net.URISyntaxException;

import http.was.controller.annotation.Controller;
import http.was.controller.annotation.RequestMapping;
import http.was.exception.HttpRequestMethodNotSupportedException;
import http.was.http.HttpMethod;
import http.was.http.request.Request;
import http.was.http.response.Response;

@Controller
public class IndexController {
    @RequestMapping(path = "/", method = HttpMethod.GET)
    public void doGet(Request request, Response response) throws IOException, URISyntaxException {
        response.ok("/index.html", HTML.getContentType());
    }

    @RequestMapping(path = "/", method = HttpMethod.POST)
    public void doPost(Request request, Response response) {
        throw new HttpRequestMethodNotSupportedException(HttpMethod.POST);
    }
}
