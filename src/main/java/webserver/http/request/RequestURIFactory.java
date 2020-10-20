package webserver.http.request;

@FunctionalInterface
public interface RequestURIFactory {
    RequestURI create(String uri);
}
