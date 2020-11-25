package dto;

import controller.Controller;

public class UrlMappingController {

    private final String url;
    private final Controller controller;

    private UrlMappingController(final String url, final Controller controller) {
        this.url = url;
        this.controller = controller;
    }

    public static UrlMappingController of(final String url, final Controller controller) {
        return new UrlMappingController(url, controller);
    }

    public String getUrl() {
        return url;
    }

    public Controller getController() {
        return controller;
    }
}
