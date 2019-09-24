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
    public void process(DataOutputStream dos, String viewName, HttpResponse httpResponse) {
        ResponseProcessor responseProcessor = ResponseProcessor.getInstance();
        String location = getLocation(viewName);
        httpResponse.setLocation(location);
        responseProcessor.sendRedirect(dos, httpResponse);
    }

    private String getLocation(String viewName) {
        return viewName.split(REDIRECT_SEPARATOR)[1];
    }
}
