package webserver.viewProcessor;

import webserver.httpResponse.HttpResponse;

public class CssViewProcessor implements ViewProcessor {
    @Override
    public boolean isSupported(String viewName) {
        if (viewName.endsWith(".css")) {
            return true;
        }
        return false;
    }

    @Override
    public String process(String viewName, HttpResponse httpResponse) {
        httpResponse.forward();
        httpResponse.setContentType("text/css");
        return "./static" + viewName;
    }
}
