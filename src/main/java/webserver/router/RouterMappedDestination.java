package webserver.router;

import utils.parser.json.JsonObject;

import java.util.Collections;
import java.util.Map;

public class RouterMappedDestination {
    private final String controller;
    private final String method;
    private final Map<String, String> pathVars;

    public RouterMappedDestination(JsonObject dest) {
        this.controller = (String) dest.get("controller").val();
        this.method = (String) dest.get("method").val();
        this.pathVars = Collections.emptyMap();
    }

    public RouterMappedDestination(String controller, String method, Map<String, String> pathVars) {
        this.controller = controller;
        this.method = method;
        this.pathVars = Collections.unmodifiableMap(pathVars);
    }

    public String controller() {
        return this.controller;
    }

    public String method() {
        return this.method;
    }

    public Map<String, String> pathVars() {
        return this.pathVars;
    }
}