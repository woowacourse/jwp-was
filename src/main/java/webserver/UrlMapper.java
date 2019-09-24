package webserver;

import webserver.Controller.*;
import webserver.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class UrlMapper {
    private static Map<String, Controller> urlMapper = new HashMap<>();

    static {
        urlMapper.put("/index.html", new MainController());
        urlMapper.put("/user/form.html", new UserController());
        urlMapper.put("/user/create", new SignUpController());
        urlMapper.put("/static/css", new StyleSheetController());
    }

    public static Controller getController(HttpRequest httpRequest) {
        if (httpRequest.getRequestHeader().get("Accept").contains("text/css")) {
            return urlMapper.get("/static/css");
        }

        String source = httpRequest.getSource();
        if (urlMapper.containsKey(source)) {
            return urlMapper.get(source);
        }
        throw new IllegalArgumentException("404");
    }
}
