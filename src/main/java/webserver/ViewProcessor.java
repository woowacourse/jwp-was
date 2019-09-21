package webserver;

import webserver.httpResponse.HttpResponse;

public class ViewProcessor {

    public String process(String viewName, HttpResponse httpResponse) {
        if (viewName.startsWith("/redirect:")) {
            httpResponse.sendRedirect();
            String[] split = viewName.split(":");
            return split[1];
        }

        if (viewName.endsWith(".html")) {
            httpResponse.forward();
            httpResponse.setContentType("text/html");
            return "./templates" + viewName;
        }

        if (viewName.endsWith(".css")) {
            httpResponse.forward();
            httpResponse.setContentType("text/css");
        }

        if (viewName.endsWith(".js")) {
            httpResponse.forward();
            httpResponse.setContentType("application/javascript");
        }

        httpResponse.forward();
        httpResponse.setContentType("text/plain");

        return "./static" + viewName;
    }
}
