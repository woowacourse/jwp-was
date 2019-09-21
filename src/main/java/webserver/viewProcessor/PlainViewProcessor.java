package webserver.viewProcessor;

import webserver.httpResponse.HttpResponse;

public class PlainViewProcessor implements ViewProcessor {

    @Override
    public boolean isSupported(String viewName) {
        return true;
    }

    @Override
    public String process(String viewName, HttpResponse httpResponse) {
        httpResponse.forward();
        httpResponse.setContentType("text/plain");
        return "./static" + viewName;
    }
}
