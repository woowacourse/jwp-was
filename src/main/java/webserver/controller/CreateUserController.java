package webserver.controller;

import webserver.http.body.HttpBody;
import webserver.http.header.HttpHeader;
import webserver.http.header.HttpHeaderField;
import webserver.http.message.HttpMessage;
import webserver.http.message.HttpRequestMessage;
import webserver.http.response.HttpStatus;
import webserver.http.response.StatusLine;
import webserver.service.UserService;

public class CreateUserController implements Controller {

    @Override
    public HttpMessage createHttpResponseMessage(HttpRequestMessage httpRequestMessage) {
        HttpBody httpBody = httpRequestMessage.getHttpBody();
        UserService.addUser(httpBody);

        StatusLine statusLine = new StatusLine(HttpStatus.FOUND);
        String redirectUrl = "/index.html";
        HttpHeader httpHeader = new HttpHeader.Builder()
                .addHeader(HttpHeaderField.LOCATION.getName(), redirectUrl)
                .build();

        return new HttpMessage(statusLine, httpHeader);
    }
}
