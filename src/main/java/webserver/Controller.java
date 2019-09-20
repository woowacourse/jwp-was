package webserver;

import webserver.httpResponse.HttpResponse;

public interface Controller {
    String service(HttpRequest request);
}
