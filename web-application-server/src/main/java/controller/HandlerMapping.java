package controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dto.UrlMappingController;

public class HandlerMapping {

    private final Map<String, Controller> controllers;

    private HandlerMapping(final Map<String, Controller> controllers) {
        this.controllers = controllers;
    }

    public static HandlerMapping of(final List<UrlMappingController> urlMappingControllers) {
        return new HandlerMapping(
                urlMappingControllers.stream()
                        .collect(Collectors.toMap(UrlMappingController::getUrl, UrlMappingController::getController))
        );
    }

    public Controller get(final String url) {
        return controllers.get(url);
    }
}
