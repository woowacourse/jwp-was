package webserver.filter;

import java.nio.charset.StandardCharsets;

import http.request.ContentType;
import http.request.HttpMethod;
import http.request.RequestEntity;
import http.response.HttpStatus;
import http.response.ResponseEntity;
import utils.FileIoUtils;
import webserver.requestmapping.FileExtensionType;

public class StaticFileFilter implements Filter {
    @Override
    public boolean doFilter(RequestEntity requestEntity, ResponseEntity responseEntity) {
        String path = requestEntity.getHttpUrl().getPath();

        if (FileExtensionType.isSupportedFile(path) && requestEntity.getHttpMethod() == HttpMethod.GET) {
            String localPath = parseToLocalPath(path);
            String body = new String(FileIoUtils.loadFileFromClasspath(localPath), StandardCharsets.UTF_8);

            responseEntity.status(HttpStatus.OK)
                .version("HTTP/1.1")
                .body(body, parseContentType(path));

            return false;
        }

        return true;
    }

    private String parseToLocalPath(String path) {
        if (path.endsWith(".html") || path.endsWith(".ico")) {
            return "./templates" + path;
        }
        return "./static" + path;
    }

    private ContentType parseContentType(String path) {
        if (path.endsWith(".css")) {
            return ContentType.CSS;
        }
        return ContentType.HTML;
    }
}
