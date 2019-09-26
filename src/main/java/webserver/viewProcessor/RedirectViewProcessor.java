package webserver.viewProcessor;

import webserver.ResponseProcessor;
import webserver.ViewProcessor;
import webserver.http.HttpResponse;

import java.io.DataOutputStream;

import static webserver.controller.AbstractController.REDIRECT_VIEW;

public class RedirectViewProcessor implements ViewProcessor {

    private static final String REDIRECT_SEPARATOR = ":";

    @Override
    public boolean isSupported(String viewName) {
        return viewName.startsWith(REDIRECT_VIEW);
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
