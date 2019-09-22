package webserver.viewProcessor;

import webserver.HttpResponse;
import webserver.ViewProcessor;

public class CssViewProcessor implements ViewProcessor {
    @Override
    public boolean isSupported(String viewName) {
        return viewName.endsWith(".css");
    }

    @Override
    public String process(String viewName, HttpResponse httpResponse) {
        httpResponse.forward();
        httpResponse.setContentType("text/css");
        return "./static" + viewName;
    }
}
