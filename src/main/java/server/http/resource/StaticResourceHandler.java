package server.http.resource;

import server.http.response.HttpResponse;
import was.http.MimeType;
import server.http.request.HttpRequest;
import was.utils.FileIoUtils;

public class StaticResourceHandler implements ResourceHandler {
    @Override
    public HttpResponse handle(final HttpRequest httpRequest) {
        if (canHandle(httpRequest)) {
            String resource = httpRequest.getPath();
            // TODO: Resource Handler Factory 를 이용해 if-else 구문 제거
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
