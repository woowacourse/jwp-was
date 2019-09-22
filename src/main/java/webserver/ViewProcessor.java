package webserver;

import webserver.HttpResponse;

public interface ViewProcessor {
    boolean isSupported(String viewName);

    String process(String viewName, HttpResponse httpResponse);
}
