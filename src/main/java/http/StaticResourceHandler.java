package http;

import utils.FileIoUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class StaticResourceHandler implements ResourceHandler {
    @Override
    public HttpResponse handle(final HttpRequest httpRequest) {
        if (canHandle(httpRequest)) {
            String resource = httpRequest.getPath();
            if (resource.contains("htm")) {
                resource = "./templates" + resource;
            } else {
                resource = "./static" + resource;
            }
            HttpResponse httpResponse = new HttpResponse();
            httpResponse.forward(resource);
            return httpResponse;
        }
        throw new StaticResourceHandlingFailException();
    }

    @Override
    public boolean canHandle(final HttpRequest httpRequest) {
        return MimeType.contains(FileIoUtils.getFileExtension(httpRequest.getPath()));
    }

    public static StaticResourceHandler getInstance() {
        return StaticResourceHandler.LazyHolder.INSTANCE;
    }

    public static class LazyHolder {
        private static final StaticResourceHandler INSTANCE = new StaticResourceHandler();
    }
}
