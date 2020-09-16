package webserver.requestmapping.behavior;

import java.nio.charset.StandardCharsets;

import http.ContentType;
import http.request.RequestEntity;
import http.response.HttpStatus;
import http.response.ResponseEntity;
import utils.FileIoUtils;

public class FileMappingBehavior implements RequestBehavior {

    @Override
    public ResponseEntity behave(RequestEntity requestEntity) {
        String path = requestEntity.getHttpUrl().getPath();
        String localPath = parseToLocalPath(path);
        String body = new String(FileIoUtils.loadFileFromClasspath(localPath), StandardCharsets.UTF_8);

        return ResponseEntity.generateWithStatus(HttpStatus.OK)
            .version("HTTP/1.1")
            .body(body, parseContentType(path));
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
