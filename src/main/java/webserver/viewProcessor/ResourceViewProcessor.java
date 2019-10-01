package webserver.viewProcessor;

import utils.FileIoUtils;
import webserver.MimeType;
import webserver.ResponseProcessor;
import webserver.View;
import webserver.ViewProcessor;
import webserver.http.HttpResponse;
import webserver.http.httpRequest.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceViewProcessor implements ViewProcessor {

    private static final String STATIC_FILE_ROUTE = "./static";

    @Override
    public boolean isSupported(View view) {
        return true;
    }

    @Override
    public void process(DataOutputStream dos, View view, HttpResponse httpResponse) {
        ResponseProcessor responseProcessor = ResponseProcessor.getInstance();
        String filePath = STATIC_FILE_ROUTE + view.getName();
        byte[] bytes = null;
        try {
            bytes = FileIoUtils.loadFileFromClasspath(filePath);
        } catch (IOException | URISyntaxException | IllegalArgumentException e) {
            responseProcessor.sendError(dos, HttpStatus.NOT_FOUND, httpResponse);
        }
        httpResponse.setContentType(MimeType.values(filePath));
        httpResponse.setContentLength(bytes.length);
        responseProcessor.forward(dos, bytes, httpResponse);
    }
}
