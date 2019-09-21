package webserver.viewProcessor;

import webserver.httpResponse.HttpResponse;

public class RedirectViewProcessor implements ViewProcessor {

    @Override
    public boolean isSupported(String viewName) {
        if (viewName.startsWith("/redirect:")) {
            return true;
        }
        return false;
    }

    @Override
    public String process(String viewName, HttpResponse httpResponse) {
        httpResponse.forward();
        String[] split = viewName.split(":");
        return split[1];
    }
}
