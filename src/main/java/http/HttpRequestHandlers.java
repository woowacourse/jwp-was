package http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHandlers {
    private Map<RequestMapping, HttpRequestHandler> handlers;

    public HttpRequestHandlers() {
        handlers = new HashMap<>();
    }

    public void addHandler(RequestMapping mapping, HttpRequestHandler handler) {
        handlers.put(mapping, handler);
    }

    public ModelAndView doService(HttpRequest httpRequest) {
        return handlers.get(httpRequest.getMapping()).handle(httpRequest);
    }
}
