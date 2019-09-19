package http;

import java.util.ArrayList;
import java.util.Arrays;

public class StaticResourceHandler implements ResourceHandler {
    @Override
    public boolean handle(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        final ArrayList<String> extensions = new ArrayList<>(Arrays.asList("css", "js", "htm"));

        if (MimeType.contains(httpRequest.getPath().substring(httpRequest.getPath().lastIndexOf(".") + 1))) {
            String resource = httpRequest.getPath();
            if (resource.contains("htm")) {
                resource = "./templates" + resource;
            } else {
                resource = "./static" + resource;
            }
            httpResponse.forward(resource);
            return true;
        }
        return false;
    }

    public static StaticResourceHandler getInstance() {
        return StaticResourceHandler.LazyHolder.INSTANCE;
    }

    public static class LazyHolder {
        private static final StaticResourceHandler INSTANCE = new StaticResourceHandler();
    }
}
