package webserver;

public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
