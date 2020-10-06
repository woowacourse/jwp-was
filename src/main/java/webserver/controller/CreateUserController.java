package webserver.controller;

import webserver.http.body.HttpBody;
import webserver.http.header.HttpHeaderField;
import webserver.http.message.HttpRequestMessage;
import webserver.http.message.HttpResponseMessage;
import webserver.http.response.HttpStatus;
import webserver.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class CreateUserController implements Controller {

    @Override
    public HttpResponseMessage createHttpResponseMessage(HttpRequestMessage httpRequestMessage) {
        HttpBody httpBody = httpRequestMessage.getHttpBody();
        UserService.addUser(httpBody);

        Map<String, String> headers = new HashMap<>();
        String redirectUrl = "/index.html";
        headers.put(HttpHeaderField.LOCATION.getName(), redirectUrl);

        return HttpResponseMessage.of(HttpStatus.FOUND, headers);
    }
}
