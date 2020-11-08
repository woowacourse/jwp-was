package controller;

import http.body.HttpBody;
import http.header.HttpHeader;
import http.header.HttpHeaderName;
import http.header.HttpHeaders;
import http.message.HttpRequestMessage;
import http.message.HttpResponseMessage;
import http.response.HttpStatus;
import service.UserService;

public class CreateUserController implements Controller {

    @Override
    public HttpResponseMessage createHttpResponseMessage(HttpRequestMessage httpRequestMessage) {
        HttpBody httpBody = httpRequestMessage.getHttpBody();
        UserService.addUser(httpBody);

        String redirectUrl = "/index.html";
        HttpHeader location = HttpHeader.of(HttpHeaderName.LOCATION.getName(), redirectUrl);

        HttpHeaders httpHeaders = HttpHeaders.empty();
        httpHeaders.addHeader(location);

        return HttpResponseMessage.of(HttpStatus.FOUND, httpHeaders);
    }
}
