package webserver.controller;

import model.Users;
import utils.TemplateCreator;
import webserver.http.header.HttpCharacterEncoding;
import webserver.http.header.HttpHeaderField;
import webserver.http.message.HttpRequestMessage;
import webserver.http.message.HttpResponseMessage;
import webserver.http.request.HttpResourceType;
import webserver.http.response.HttpStatus;
import webserver.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static webserver.http.header.HttpHeader.HEADER_VALUE_DELIMITER;

public class GetUserListController implements Controller {

    @Override
    public HttpResponseMessage createHttpResponseMessage(HttpRequestMessage httpRequestMessage) {
        String cookieValue = httpRequestMessage.getHeaderValue(HttpHeaderField.COOKIE.getName());

        if (Objects.isNull(cookieValue)) {
            return createNonUserHttpResponseMessage();
        }

        if (isLogined(cookieValue)) {
            return createUserListHttpResponseMessage();
        }

        return createNonUserHttpResponseMessage();
    }

    private boolean isLogined(String cookieValue) {
        String[] tokens = cookieValue.split(HEADER_VALUE_DELIMITER);
        for (String token : tokens) {
            if (token.contains("logined")) {
                String[] loginedTokens = token.split("=");
                return Boolean.parseBoolean(loginedTokens[1]);
            }
        }

        return false;
    }

    private HttpResponseMessage createUserListHttpResponseMessage() {
        String body = createUserListBody();

        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaderField.CONTENT_TYPE.getName(),
                    HttpResourceType.HTML.getContentType() + HEADER_VALUE_DELIMITER + HttpCharacterEncoding.UTF_8.toHttpMessage());
        headers.put(HttpHeaderField.CONTENT_LENGTH.getName(), String.valueOf(body.getBytes().length));

        return HttpResponseMessage.of(HttpStatus.OK, headers, body);
    }

    private String createUserListBody() {
        String userListTemplateLocation = "/user/list";
        Users users = UserService.findAll();

        return TemplateCreator.createTemplate(userListTemplateLocation, users);
    }

    private HttpResponseMessage createNonUserHttpResponseMessage() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaderField.LOCATION.getName(), "/user/login.html");

        return HttpResponseMessage.of(HttpStatus.FOUND, headers);
    }
}
