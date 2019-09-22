package webserver.viewProcessor;

import webserver.HttpResponse;
import webserver.ViewProcessor;

import java.io.DataOutputStream;

public class ErrorViewProcessor implements ViewProcessor {

    @Override
    public boolean isSupported(String viewName) {
        return viewName.startsWith("/error:");
    }

    @Override
    public void process(DataOutputStream dos, String viewName) {
        HttpResponse httpResponse = new HttpResponse();
        String errorCode = viewName.split(":")[1];
        httpResponse.error(dos, errorCode);
    }
}
