package webserver.controller;

import model.Users;
import utils.TemplateCreator;
import webserver.http.header.HttpCharacterEncoding;
import webserver.http.header.HttpHeader;
import webserver.http.header.HttpHeaderName;
import webserver.http.header.HttpHeaders;
import webserver.http.message.HttpRequestMessage;
import webserver.http.message.HttpResponseMessage;
import webserver.http.request.HttpResourceType;
import webserver.http.response.HttpStatus;
import webserver.http.session.HttpSessionFinder;
import webserver.service.UserService;

import java.util.Objects;
import java.util.Optional;

import static webserver.http.header.HttpHeaders.HEADER_VALUE_DELIMITER;

public class GetUserListController implements Controller {

    @Override
    public HttpResponseMessage createHttpResponseMessage(HttpRequestMessage httpRequestMessage) {
        Optional<String> cookieValue = httpRequestMessage.getHeaderValue(HttpHeaderName.COOKIE.getName());
        String cookie = cookieValue.orElse(null);

        if (Objects.isNull(cookie)) {
            return createNonUserHttpResponseMessage();
        }

        if (isLogined(cookie)) {
            return createUserListHttpResponseMessage();
        }

        return createNonUserHttpResponseMessage();
    }

    private boolean isLogined(String cookie) {
        String[] tokens = cookie.split(HEADER_VALUE_DELIMITER);
        for (String token : tokens) {
            if (token.contains("sessionId")) {
                String[] sessionIdTokens = token.split("=");
                return HttpSessionFinder.hasSession(sessionIdTokens[1]);
            }
        }

        return false;
    }

    private HttpResponseMessage createUserListHttpResponseMessage() {
        String body = createUserListBody();

        HttpHeader contentType = HttpHeader.of(HttpHeaderName.CONTENT_TYPE.getName(),
                                               HttpResourceType.HTML.getContentType()
                                                       + HEADER_VALUE_DELIMITER
                                                       + HttpCharacterEncoding.UTF_8.toHttpMessage());
        HttpHeader contentLength = HttpHeader.of(HttpHeaderName.CONTENT_LENGTH.getName(),
                                                 String.valueOf(body.getBytes().length));

        HttpHeaders httpHeaders = HttpHeaders.empty();
        httpHeaders.addHeader(contentType);
        httpHeaders.addHeader(contentLength);

        return HttpResponseMessage.of(HttpStatus.OK, httpHeaders, body);
    }

    private String createUserListBody() {
        String userListTemplateLocation = "/user/list";
        Users users = UserService.findAll();

        return TemplateCreator.createTemplate(userListTemplateLocation, users);
    }

    private HttpResponseMessage createNonUserHttpResponseMessage() {
        HttpHeader location = HttpHeader.of(HttpHeaderName.LOCATION.getName(), "/user/login.html");

        HttpHeaders httpHeaders = HttpHeaders.empty();
        httpHeaders.addHeader(location);

        return HttpResponseMessage.of(HttpStatus.FOUND, httpHeaders);
    }
}
