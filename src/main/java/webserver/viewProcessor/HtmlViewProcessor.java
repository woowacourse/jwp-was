package webserver.viewProcessor;

import webserver.httpResponse.HttpResponse;

public class HtmlViewProcessor implements ViewProcessor {

    @Override
    public boolean isSupported(String viewName) {
        if (viewName.endsWith(".html")) {
            return true;
        }
        return false;
    }

    @Override
    public String process(String viewName, HttpResponse httpResponse) {
        httpResponse.forward();
        httpResponse.setContentType("text/html");
        return "./templates" + viewName;
    }
}
