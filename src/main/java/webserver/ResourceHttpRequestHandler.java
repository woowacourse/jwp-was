package webserver;

import http.common.ContentTypeMapper;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import utils.FileIoUtils;
import utils.StringUtils;
import webserver.exception.ResourceNotFoundException;

public class ResourceHttpRequestHandler {
    private static final String STATIC_RESOURCE_PATH_PREFIX = "./static";

    private static class StaticResourceHttpRequestHandlerLazyHolder {
        private static final ResourceHttpRequestHandler INSTANCE = new ResourceHttpRequestHandler();
    }

    public void handleHttpRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        String filePath = STATIC_RESOURCE_PATH_PREFIX + httpRequest.getPath();
        try {
            byte[] file = FileIoUtils.loadFileFromClasspath(filePath);

            httpResponse.setResponseStatus(ResponseStatus.OK);
            httpResponse.addHeaderAttribute("Content-Type", ContentTypeMapper.getContentType(filePath));
            httpResponse.addHeaderAttribute("Content-Length", String.valueOf(file.length));
            httpResponse.setBody(file);
        } catch (NullPointerException e) {
            throw new ResourceNotFoundException(e);
        }
    }

    public boolean canHandle(String path) {
        String[] url = StringUtils.split(path, "/");
        return StringUtils.isNotBlank(url) && url[url.length - 1].matches("^[^/:*?<>|\"\\\\]+[.][a-zA-Z0-9]+$");
    }

    public static ResourceHttpRequestHandler getInstance() {
        return StaticResourceHttpRequestHandlerLazyHolder.INSTANCE;
    }
}
