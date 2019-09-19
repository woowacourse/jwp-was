package webserver;

public interface Controller {
    Object service(HttpRequest request, HttpResponse response);
}
