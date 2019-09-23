package webserver.viewProcessor;

import webserver.HttpResponse;
import webserver.ResponseProcessor;
import webserver.ViewProcessor;

import java.io.DataOutputStream;

public class ErrorViewProcessor implements ViewProcessor {

    private static final String ERROR_CODE_SEPARATOR = ":";

    @Override
    public boolean isSupported(String viewName) {
        return viewName.startsWith("/sendError:");
    }

    @Override
    public void process(DataOutputStream dos, String viewName) {
        ResponseProcessor responseProcessor = ResponseProcessor.getInstance();
        HttpResponse httpResponse = new HttpResponse();
        String errorCode = viewName.split(ERROR_CODE_SEPARATOR)[1];
        responseProcessor.sendError(dos, errorCode, httpResponse);
    }
}
