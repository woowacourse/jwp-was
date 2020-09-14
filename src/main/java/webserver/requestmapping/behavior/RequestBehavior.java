package webserver.requestmapping.behavior;

import java.io.InputStream;

import http.request.RequestEntity;

public interface RequestBehavior {
    InputStream behave(RequestEntity requestEntity);
}
