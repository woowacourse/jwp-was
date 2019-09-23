package webserver.viewProcessor;

import webserver.HttpResponse;
import webserver.ResponseProcessor;
import webserver.ViewProcessor;

import java.io.DataOutputStream;

public class RedirectViewProcessor implements ViewProcessor {

    @Override
    public boolean isSupported(String viewName) {
        return viewName.startsWith("/redirect:");
    }

    @Override
    public void process(DataOutputStream dos, String viewName) {
        ResponseProcessor responseProcessor = ResponseProcessor.getInstance();
        HttpResponse httpResponse = new HttpResponse();
        String[] split = viewName.split(":");
        String path = split[1];
        responseProcessor.sendRedirect(dos, path, httpResponse);
    }
}
