package webserver.viewProcessor;

import webserver.HttpResponse;
import webserver.ResponseProcessor;
import webserver.ViewProcessor;

import java.io.DataOutputStream;

public class ErrorViewProcessor implements ViewProcessor {

    private static final String ERROR_CODE_SEPARATOR = ":";

    @Override
    public boolean isSupported(String viewName) {
        return viewName.startsWith("/error:");
    }

    @Override
    public void process(DataOutputStream dos, String viewName, HttpResponse httpResponse) {
        ResponseProcessor responseProcessor = ResponseProcessor.getInstance();
        String errorCode = getErrorCode(viewName);
        responseProcessor.sendError(dos, errorCode, httpResponse);
    }

    private String getErrorCode(String viewName) {
        return viewName.split(ERROR_CODE_SEPARATOR)[1];
    }
}
