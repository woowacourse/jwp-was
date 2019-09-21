package webserver.viewProcessor;

import webserver.httpResponse.HttpResponse;

public interface ViewProcessor {
    boolean isSupported(String viewName);

    String process(String viewName, HttpResponse httpResponse);
}
