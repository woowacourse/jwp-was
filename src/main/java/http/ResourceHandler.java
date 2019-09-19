package http;

public interface ResourceHandler {
    boolean handle(HttpRequest httpRequest, HttpResponse httpResponse);
}
