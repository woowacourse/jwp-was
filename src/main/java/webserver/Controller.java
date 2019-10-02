package webserver;

import webserver.http.HttpRequest;

public interface Controller {
    View service(HttpRequest request);
}
