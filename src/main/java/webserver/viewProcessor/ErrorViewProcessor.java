package webserver.viewProcessor;

import webserver.ResponseProcessor;
import webserver.View;
import webserver.ViewProcessor;
import webserver.http.HttpResponse;

import java.io.DataOutputStream;

import static webserver.controller.AbstractController.ERROR_VIEW;

public class ErrorViewProcessor implements ViewProcessor {

    private static final String ERROR_CODE_SEPARATOR = ":";

    @Override
    public boolean isSupported(View view) {
        return view.nameStartedWith(ERROR_VIEW);
    }

    @Override
    public void process(DataOutputStream dos, View view, HttpResponse httpResponse) {
        ResponseProcessor responseProcessor = ResponseProcessor.getInstance();
        String errorCode = getErrorCode(view);
        responseProcessor.sendError(dos, errorCode, httpResponse);
    }

    private String getErrorCode(View view) {
        String viewName = view.getName();
        return viewName.split(ERROR_CODE_SEPARATOR)[1];
    }
}
