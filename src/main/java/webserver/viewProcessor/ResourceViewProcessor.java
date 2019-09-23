package webserver.viewProcessor;

import utils.FileIoUtils;
import webserver.HttpResponse;
import webserver.MimeType;
import webserver.ResponseProcessor;
import webserver.ViewProcessor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceViewProcessor implements ViewProcessor {

    private static final String STATIC_FILE_ROUTE = "./static";

    @Override
    public boolean isSupported(String viewName) {
        return true;
    }

    @Override
    public void process(DataOutputStream dos, String viewName) {
        HttpResponse httpResponse = new HttpResponse();
        ResponseProcessor responseProcessor = ResponseProcessor.getInstance();
        String filePath = STATIC_FILE_ROUTE + viewName;
        try {
            byte[] bytes = FileIoUtils.loadFileFromClasspath(filePath);
            String type = MimeType.values(filePath);
            httpResponse.setContentType(type);
            responseProcessor.forward(dos, bytes, httpResponse);
        } catch (IOException | URISyntaxException e) {
            responseProcessor.sendError(dos, "404", httpResponse);
        }
    }
}
