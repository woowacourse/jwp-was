package webserver;

import webserver.Controller.*;
import webserver.Controller.exception.ResourceNotFoundException;
import webserver.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class UrlMapper {
    private static final String HEADER_FIELD_ACCEPT = "Accept";
    private static final String CONTENT_TYPE_CSS = "text/css";
    private static final String PATH_CSS = "/static/css";
    private static Map<String, Controller> urlMapper = new HashMap<>();

    static {
        urlMapper.put("/index.html", new MainController());
        urlMapper.put("/user/form.html", new UserController());
        urlMapper.put("/user/create", new SignUpController());
        urlMapper.put(PATH_CSS, new StyleSheetController());
    }

    public static Controller getController(HttpRequest httpRequest) {
        if (httpRequest.getRequestHeader().getHeaderFieldValue(HEADER_FIELD_ACCEPT).contains(CONTENT_TYPE_CSS)) {
            return urlMapper.get(PATH_CSS);
        }

        String source = httpRequest.getSource();
        if (urlMapper.containsKey(source)) {
            return urlMapper.get(source);
        }
        throw new ResourceNotFoundException("Resource Not found.");
    }
}
