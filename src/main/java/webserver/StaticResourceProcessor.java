package webserver;

import utils.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.httpRequest.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class StaticResourceProcessor {

    private static final String STATIC_FILE_ROUTE = "./static";
    private static final String STATIC_HTML_ROUTE = "./templates";
    private static final String HTML_SUFFIX = ".html";

    public boolean isSupported(HttpRequest httpRequest) {
        return httpRequest.getPath().contains(".");
    }

    public void process(DataOutputStream dos, HttpRequest httpRequest) {
        ResponseProcessor responseProcessor = ResponseProcessor.getInstance();
        HttpResponse httpResponse = new HttpResponse(httpRequest);
        String filePath = getFullPath(httpRequest.getPath());
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

    private String getFullPath(String path) {
        if (path.endsWith(HTML_SUFFIX)) {
            return STATIC_HTML_ROUTE + path;
        } else {
            return STATIC_FILE_ROUTE + path;
        }
    }
}
