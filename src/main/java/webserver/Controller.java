package webserver;

public interface Controller {

    void service(HttpRequest request, HttpResponse response);

    String getPath();
}
