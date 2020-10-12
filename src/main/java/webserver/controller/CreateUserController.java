package webserver.controller;

import webserver.http.body.HttpBody;
import webserver.http.header.HttpHeader;
import webserver.http.header.HttpHeaderName;
import webserver.http.header.HttpHeaders;
import webserver.http.message.HttpRequestMessage;
import webserver.http.message.HttpResponseMessage;
import webserver.http.response.HttpStatus;
import webserver.service.UserService;

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
