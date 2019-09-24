package webserver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceHttpRequestHandler {
    private static final String STATIC_RESOURCE_PATH_PREFIX = "./static";

    private static class StaticResourceHttpRequestHandlerLazyHolder {
        private static final ResourceHttpRequestHandler INSTANCE = new ResourceHttpRequestHandler();
    }

    public void handleHttpRequest(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String filePath = STATIC_RESOURCE_PATH_PREFIX + httpRequest.getPath();
        byte[] file = FileIoUtils.loadFileFromClasspath(filePath);

        //TODO Content Type 추출
        String contentType = "";
        httpResponse.setResponseStatus(ResponseStatus.OK);
        httpResponse.addHeaderAttribute("Content-Type", contentType);
        httpResponse.addHeaderAttribute("Content-Length", String.valueOf(file.length));
        httpResponse.setBody(file);
    }

    public boolean canHandle(HttpRequest httpRequest) {
        String[] url = httpRequest.getPath().split("/");

        return url.length > 0 && url[url.length - 1].matches("^[^/:*?<>|\"\\\\]+[.][a-zA-Z0-9]+$");
    }

    public static ResourceHttpRequestHandler getInstance() {
        return StaticResourceHttpRequestHandlerLazyHolder.INSTANCE;
    }
}
