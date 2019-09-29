package webserver.viewProcessor;

import webserver.ResponseProcessor;
import webserver.View;
import webserver.ViewProcessor;
import webserver.http.HttpResponse;

import java.io.DataOutputStream;

import static webserver.controller.AbstractController.REDIRECT_VIEW;

public class RedirectViewProcessor implements ViewProcessor {

    private static final String REDIRECT_SEPARATOR = ":";

    @Override
    public boolean isSupported(View view) {
        return view.nameStartedWith(REDIRECT_VIEW);
    }

    @Override
    public void process(DataOutputStream dos, View view, HttpResponse httpResponse) {
        ResponseProcessor responseProcessor = ResponseProcessor.getInstance();
        String location = getLocation(view);
        httpResponse.setLocation(location);
        responseProcessor.sendRedirect(dos, httpResponse);
    }

    private String getLocation(View view) {
        String viewName = view.getName();
        return viewName.split(REDIRECT_SEPARATOR)[1];
    }
}
