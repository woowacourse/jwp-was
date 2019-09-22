package webserver.viewProcessor;

import webserver.HttpResponse;
import webserver.ViewProcessor;

public class JsViewProcessor implements ViewProcessor {

    @Override
    public boolean isSupported(String viewName) {
        return viewName.endsWith(".js");

    }

    @Override
    public String process(String viewName, HttpResponse httpResponse) {
        httpResponse.forward();
        httpResponse.setContentType("application/javascript");
        return "./static" + viewName;
    }
}
