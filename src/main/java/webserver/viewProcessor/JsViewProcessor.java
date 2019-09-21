package webserver.viewProcessor;

import webserver.httpResponse.HttpResponse;

public class JsViewProcessor implements ViewProcessor {

    @Override
    public boolean isSupported(String viewName) {
        if (viewName.endsWith(".js")) {
            return true;
        }
        return false;
    }

    @Override
    public String process(String viewName, HttpResponse httpResponse) {
        httpResponse.forward();
        httpResponse.setContentType("application/javascript");
        return "./static" + viewName;
    }
}
