package http;

public interface HttpRequestHandler {
    ModelAndView handle(HttpRequest httpRequest);
}
