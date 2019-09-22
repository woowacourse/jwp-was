package webserver.viewProcessor;

import webserver.HttpResponse;
import webserver.ViewProcessor;

import java.io.DataOutputStream;

public class RedirectViewProcessor implements ViewProcessor {

    @Override
    public boolean isSupported(String viewName) {
        return viewName.startsWith("/redirect:");
    }

    @Override
    public void process(DataOutputStream dos, String viewName) {
        HttpResponse httpResponse = new HttpResponse();
        String[] split = viewName.split(":");
        String path = split[1];
        httpResponse.sendRedirect(dos, path);
    }
}
