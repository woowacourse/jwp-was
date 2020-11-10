package webserver.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;

public class ResponseHeader {

    private static final Logger log = LoggerFactory.getLogger(ResponseHeader.class);

    private final Map<String, String> headers;

    public ResponseHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public void putHeader(String key, String value) {
        headers.put(key, value);
    }

    public void write(DataOutputStream dataOutputStream) {
        headers.forEach((key, value) -> {
            try {
                dataOutputStream
                    .writeBytes(key + ": " + value + StringUtils.SPACE + System.lineSeparator());
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        });
    }
}
