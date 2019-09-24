package webserver.viewProcessor;

import utils.FileIoUtils;
import webserver.HttpResponse;
import webserver.MimeType;
import webserver.ResponseProcessor;
import webserver.ViewProcessor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class HtmlViewProcessor implements ViewProcessor {

    private static final String HTML_ROUTE = "./templates";

    @Override
    public boolean isSupported(String viewName) {
        return viewName.endsWith(".html");
    }

    @Override
    public void process(DataOutputStream dos, String viewName, HttpResponse httpResponse) {
        ResponseProcessor responseProcessor = ResponseProcessor.getInstance();
        String filePath = HTML_ROUTE + viewName;
        byte[] bytes = null;
        try {
            bytes = FileIoUtils.loadFileFromClasspath(filePath);
        } catch (IOException | URISyntaxException | IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
        setResponseBody(httpResponse, filePath, bytes);
        responseProcessor.forward(dos, bytes, httpResponse);
    }

    private void setResponseBody(HttpResponse httpResponse, String filePath, byte[] bytes) {
        httpResponse.setContentType(MimeType.values(filePath));
        httpResponse.setContentLength(bytes.length);
    }
}
