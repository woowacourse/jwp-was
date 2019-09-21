package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResponseBody {
    private static final Logger log = LoggerFactory.getLogger(ResponseBody.class);

    private byte[] body;

    public ResponseBody(String path) {
        try {
            this.body = FileIoUtils.loadFileFromClasspath(path);
        } catch (IOException | URISyntaxException e) {
            log.error("{}, {}", path, e.getMessage());
        }
    }

    public int getLength() {
        return body.length;
    }

    public byte[] getBody() {
        return body;
    }
}
