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
        HttpResponseLine httpResponseLine = new HttpResponseLine(HttpStatus.FOUND);
        HttpResponseHeader httpResponseHeader = new HttpResponseHeader(httpResponseLine);
        httpResponseHeader.add("Location", "http://localhost:8080/index.html");
        return new HttpResponse(httpResponseHeader);
    }
}
