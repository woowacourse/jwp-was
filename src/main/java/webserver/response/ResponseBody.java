package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.utils.FileIoUtils;
import webserver.utils.FilePathUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResponseBody {
    private static final Logger log = LoggerFactory.getLogger(ResponseBody.class);

    private String path;
    private byte[] body;

    public ResponseBody(String path) {
        this.path = path;
        try {
            this.body = FileIoUtils.loadFileFromClasspath(FilePathUtils.getResourcePath(path));
        } catch (IOException | URISyntaxException e) {
            log.error("{}, {}", path, e.getMessage());
        }
    }

    public int getBodyLength() {
        return body.length;
    }

    public byte[] getBody() {
        return body;
    }

    public String getPath() {
        return path;
    }
}
