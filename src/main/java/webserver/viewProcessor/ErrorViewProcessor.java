package webserver.viewProcessor;

import webserver.ResponseProcessor;
import webserver.ViewProcessor;
import webserver.http.HttpResponse;

import java.io.DataOutputStream;

import static webserver.controller.AbstractController.ERROR_VIEW;

public class ErrorViewProcessor implements ViewProcessor {

    private static final String ERROR_CODE_SEPARATOR = ":";

    @Override
    public boolean isSupported(String viewName) {
        return viewName.startsWith(ERROR_VIEW);
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
