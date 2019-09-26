package webserver;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface Controller {
    String service(HttpRequest request, HttpResponse response);
}
