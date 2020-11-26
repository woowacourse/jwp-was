package webserver.filter;

import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.IOException;
import utils.ContentTypeMatcher;
import utils.FileIoUtils;

public class ServeStatic implements Filter {
    private final String root;

    public ServeStatic(String root) {
        this.root = root;
    }

    @Override
    public void doFilter(HttpRequest request, HttpResponse response, FilterChain chain)
        throws IOException {
        String filePath = "./" + root + request.path();
        if (FileIoUtils.exists(filePath)) {
            response.status(HttpStatus.OK)
                .contentType(ContentTypeMatcher.match(request.path()) + ";charset=utf-8")
                .end(FileIoUtils.loadFileFromClasspath(filePath));
            return;
        }
        chain.doFilter(request, response);
    }
}
