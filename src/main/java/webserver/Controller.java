package webserver;

public interface Controller {
    String service(HttpRequest request, HttpResponse response);
}
