package webserver;

import webserver.request.ServletRequest;
import webserver.response.ServletResponse;

public interface StaticResourceHandler {

    boolean isStaticResourceRequest(ServletRequest request);

    ServletResponse handle(ServletRequest request);
}
