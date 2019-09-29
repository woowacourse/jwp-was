package webserver.httprequesthandler;

import controller.exception.MethodNotAllowedException;
import http.common.ContentTypeMapper;
import http.request.HttpRequest;
import http.request.RequestMethod;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import utils.FileIoUtils;
import utils.StringUtils;

public class HttpResourceRequestHandler extends AbstractHttpRequestHandler {
    private static final String STATIC_RESOURCE_PATH_PREFIX = "./static";

    private HttpResourceRequestHandler() {
    }

    public static HttpResourceRequestHandler getInstance() {
        return StaticResourceHttpRequestHandlerLazyHolder.INSTANCE;
    }

    @Override
    public void handleInternal(HttpRequest httpRequest, HttpResponse httpResponse) {
        checkRequestMethod(httpRequest.getMethod());

        String filePath = STATIC_RESOURCE_PATH_PREFIX + httpRequest.getPath();
        try {
            byte[] file = FileIoUtils.loadFileFromClasspath(filePath);

            httpResponse.setResponseStatus(ResponseStatus.OK);
            httpResponse.addHeaderAttribute("Content-Type", ContentTypeMapper.getContentType(filePath));
            httpResponse.addHeaderAttribute("Content-Length", String.valueOf(file.length));
            httpResponse.setBody(file);
        } catch (NullPointerException e) {
            e.printStackTrace();
            httpResponse.setResponseStatus(ResponseStatus.NOT_FOUND);
        }
    }

    private void checkRequestMethod(RequestMethod method) {
        if (!RequestMethod.GET.equals(method)) {
            throw new MethodNotAllowedException();
        }
    }

    @Override
    public boolean canHandle(String path) {
        String[] url = StringUtils.split(path, "/");
        return StringUtils.isNotBlank(url) && url[url.length - 1].matches("^[^/:*?<>|\"\\\\]+[.][a-zA-Z0-9]+$");
    }

    private static class StaticResourceHttpRequestHandlerLazyHolder {
        private static final HttpResourceRequestHandler INSTANCE = new HttpResourceRequestHandler();
    }
}
