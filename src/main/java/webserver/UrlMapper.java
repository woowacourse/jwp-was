package webserver;

import controller.*;
import controller.exception.HttpRequestException;
import webserver.common.HttpStatus;
import webserver.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;

import static webserver.support.ConStants.CONTENT_TYPE_CSS;
import static webserver.support.ConStants.HEADER_FIELD_ACCEPT;

public class UrlMapper {
    private static final String PATH_CSS = "/static/css";
    private static Map<String, Controller> urlMapper = new HashMap<>();

    static {
        urlMapper.put("/index", new MainController());
        urlMapper.put("/user/form", new UserController());
        urlMapper.put("/user/create", new SignUpController());
        urlMapper.put(PATH_CSS, new StyleSheetController());
        urlMapper.put("/user/login", new SignInController());
        urlMapper.put("/user/login_failed", new FailToSignInController());
        urlMapper.put("/user/list", new UserListController());
    }

    public static Controller getController(HttpRequest httpRequest) {
        if (httpRequest.containHeaderField(HEADER_FIELD_ACCEPT, CONTENT_TYPE_CSS)) {
            return urlMapper.get(PATH_CSS);
        }

        String source = httpRequest.getSource().split("\\.")[0];
        if (urlMapper.containsKey(source)) {
            return urlMapper.get(source);
        }
        throw new HttpRequestException(HttpStatus.NOT_FOUND);
    }
}
