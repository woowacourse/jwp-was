package controller;

import http.header.HttpCharacterEncoding;
import http.header.HttpHeader;
import http.header.HttpHeaderName;
import http.header.HttpHeaders;
import http.header.cookie.HttpCookies;
import http.message.HttpRequestMessage;
import http.message.HttpResponseMessage;
import http.request.HttpResourceType;
import http.response.HttpStatus;
import http.session.HttpSessionFinder;
import model.Users;
import service.UserService;
import utils.TemplateCreator;

import java.util.Optional;

public class GetUserListController implements Controller {

    @Override
    public HttpResponseMessage createHttpResponseMessage(HttpRequestMessage httpRequestMessage) {
        Optional<String> sessionId = httpRequestMessage.getHeaderValue(HttpHeaderName.COOKIE.getName())
                .map(HttpCookies::from)
                .flatMap(httpCookies -> httpCookies.getCookieValue("sessionId"));

        if (!sessionId.isPresent()) {
            return createNonUserHttpResponseMessage();
        }

        return sessionId.filter(HttpSessionFinder::hasSession)
                .map(id -> createUserListHttpResponseMessage())
                .orElse(createNonUserHttpResponseMessage());
    }

    private HttpResponseMessage createUserListHttpResponseMessage() {
        String body = createUserListBody();

        HttpHeader contentType = HttpHeader.of(HttpHeaderName.CONTENT_TYPE.getName(),
                                               HttpResourceType.HTML.getContentType()
                                                       + HttpHeaders.HEADER_VALUE_DELIMITER
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
