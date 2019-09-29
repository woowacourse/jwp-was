package webserver;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface Controller {
    View service(HttpRequest request, HttpResponse response);
}
