package webserver;

import utils.parser.JsonObject;

public class RoutedDestination {
    private final String controller;
    private final String method;

    public RoutedDestination(JsonObject dest) {
        this.controller = (String) dest.get("controller").get().val();
        this.method = (String) dest.get("method").get().val();
    }

    public String controller() {
        return this.controller;
    }

    public String method() {
        return this.method;
    }
}