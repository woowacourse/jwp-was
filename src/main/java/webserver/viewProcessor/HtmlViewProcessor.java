package webserver.viewProcessor;

import webserver.HttpResponse;
import webserver.ViewProcessor;

public class HtmlViewProcessor implements ViewProcessor {

    @Override
    public boolean isSupported(String viewName) {
        return viewName.endsWith(".html");
    }

    @Override
    public String process(String viewName, HttpResponse httpResponse) {
        httpResponse.forward();
        httpResponse.setContentType("text/html");
        return "./templates" + viewName;
    }
}
