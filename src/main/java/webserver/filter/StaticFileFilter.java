package webserver.filter;

import java.nio.charset.StandardCharsets;

import http.request.HttpMethod;
import http.request.RequestEntity;
import http.response.HttpStatus;
import http.response.ResponseEntity;
import utils.FileIoUtils;

public class StaticFileFilter implements Filter {
    @Override
    public boolean doFilter(RequestEntity requestEntity, ResponseEntity responseEntity) {
        String path = requestEntity.getHttpUrl().getPath();

        if (FileExtensionType.isSupportedFile(path) && requestEntity.getHttpMethod() == HttpMethod.GET) {
            String localPath = FileExtensionType.findLocalPath(path);
            String body = new String(FileIoUtils.loadFileFromClasspath(localPath), StandardCharsets.UTF_8);

            responseEntity.status(HttpStatus.OK)
                .version("HTTP/1.1")
                .body(body, FileExtensionType.findMatchingContentType(path));
            return false;
        }
        return true;
    }
}
