package servlet;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.FileIoUtils;
import webserver.ViewResolver;

import java.util.ArrayList;
import java.util.List;

import static http.response.HttpStatus.NOT_FOUND;

public class DefaultServlet implements Servlet {
    private static final List<String> FILE_PATH_PREFIXES;

    static {
        FILE_PATH_PREFIXES = new ArrayList<>();
        FILE_PATH_PREFIXES.add("./static");
        FILE_PATH_PREFIXES.add("./templates");
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        for (String prefix : FILE_PATH_PREFIXES) {
            String staticFilePath = prefix + request.getUri().getPath();
            if (FileIoUtils.existFileInClasspath(staticFilePath)) {
                ViewResolver.resolveWithViewPath(request, response, staticFilePath);
                return;
            }
        }
        response.error(NOT_FOUND);
    }
}
