package webserver.requestmapping.behavior;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.github.jknack.handlebars.internal.lang3.ArrayUtils;
import http.request.RequestEntity;
import utils.FileIoUtils;

public class FileMappingBehavior implements RequestBehavior {

    @Override
    public InputStream behave(RequestEntity requestEntity) {
        String path = requestEntity.getHttpUrl().getPath();
        String localPath = parseToLocalPath(path);
        byte[] body = FileIoUtils.loadFileFromClasspath(localPath);
        String header =
            "HTTP/1.1 200 OK \r\n" +
                "Content-Type: " + parseContentType(path) + "\r\n" +
                "Content-Length: " + body.length + "\r\n" +
                "\r\n";

        byte[] response = ArrayUtils.addAll(header.getBytes(), body);
        return new ByteArrayInputStream(response);
    }

    private String parseToLocalPath(String path) {
        if (path.endsWith(".html") || path.endsWith(".ico")) {
            return "./templates" + path;
        }
        return "./static" + path;
    }

    private String parseContentType(String path) {
        if (path.endsWith(".css")) {
            return "text/css";
        }
        return "text/html;charset=utf-8";
    }
}
