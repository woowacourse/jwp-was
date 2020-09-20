package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseHeader;
import webserver.http.response.HttpResponseLine;
import webserver.http.response.HttpStatus;
import webserver.service.UserService;

public class UserController {
    public static HttpResponse doPost(HttpRequest httpRequest) {
        UserService.join(httpRequest.getBody());

        byte[] body = StaticFileController.findFile("./templates/index.html");

        HttpResponseLine httpResponseLine = new HttpResponseLine(HttpStatus.REDIRECT);
        HttpResponseHeader httpResponseHeader = new HttpResponseHeader(httpResponseLine);
        httpResponseHeader.add("Content-Type", "text/html;charset=utf-8");
        httpResponseHeader.add("Content-Length", String.valueOf(body.length));
        return new HttpResponse(httpResponseHeader, body);
    }
}
