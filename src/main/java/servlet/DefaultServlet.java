package servlet;

import http.request.HttpRequest;
import http.response.HttpResponseEntity;
import utils.FileIoUtils;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class DefaultServlet implements Servlet {
    private static final List<String> FILE_PATH_PREFIXES;

    static {
        FILE_PATH_PREFIXES = new ArrayList<>();
        FILE_PATH_PREFIXES.add("./static");
        FILE_PATH_PREFIXES.add("./templates");
    }

    @Override
    public HttpResponseEntity handle(HttpRequest httpRequest) throws URISyntaxException {
        for (String prefix : FILE_PATH_PREFIXES) {
            String staticFilePath = prefix + httpRequest.getUri().getPath();
            if (FileIoUtils.existFileInClasspath(staticFilePath)) {
                return HttpResponseEntity.get200Response(staticFilePath);
            }
        }
        return HttpResponseEntity.get404Response();
    }
}
