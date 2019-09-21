package http;

public interface ResourceHandler {
    HttpResponse handle(HttpRequest httpRequest);
    boolean canHandle(HttpRequest httpRequest);
}
