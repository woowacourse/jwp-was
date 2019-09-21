package http;

public interface Servlet {
    HttpResponse service(HttpRequest httpRequest);
}
