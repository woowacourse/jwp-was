package http;

public interface Servlet {
    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
