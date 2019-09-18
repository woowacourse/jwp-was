package http;

public interface HandlerMapping {
    ModelAndView handle(HttpRequest httpRequest);
}
