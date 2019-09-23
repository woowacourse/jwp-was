package webserver.viewProcessor;

import webserver.HttpResponse;
import webserver.ResponseProcessor;
import webserver.ViewProcessor;

import java.io.DataOutputStream;

public class RedirectViewProcessor implements ViewProcessor {

    private static final String REDIRECT_SEPARATOR = ":";

    @Override
    public boolean isSupported(String viewName) {
        return viewName.startsWith("/redirect:");
    }

    @Override
    public void process(DataOutputStream dos, String viewName) {
        ResponseProcessor responseProcessor = ResponseProcessor.getInstance();
        HttpResponse httpResponse = new HttpResponse();
        String[] split = viewName.split(REDIRECT_SEPARATOR);
        String path = split[1];
        responseProcessor.sendRedirect(dos, path, httpResponse);
    }
}
