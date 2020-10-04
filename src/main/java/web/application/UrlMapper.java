package web.application;

import java.util.HashMap;
import java.util.Map;

import web.application.controller.Controller;
import web.application.controller.CreateUserController;
import web.application.controller.RootController;

public class UrlMapper {

    private Map<String, Controller> mapper;

    public UrlMapper() {
        this.mapper = new HashMap<>();
        this.mapper.put("/user/create", CreateUserController.getInstance());
        this.mapper.put("/", RootController.getInstance());
    }

    public static UrlMapper getInstance() {
        return Cache.URL_MAPPER;
    }

    public Controller getController(String url) {
        return this.mapper.get(url);
    }

    public boolean contains(String url) {
        return this.mapper.containsKey(url);
    }

    private static class Cache {
        private static final UrlMapper URL_MAPPER = new UrlMapper();
    }
}
