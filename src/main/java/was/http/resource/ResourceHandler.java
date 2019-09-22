package was.http.resource;

import was.http.response.HttpResponse;
import was.http.request.HttpRequest;

public interface ResourceHandler {
    HttpResponse handle(HttpRequest httpRequest);
    boolean canHandle(HttpRequest httpRequest);
}
