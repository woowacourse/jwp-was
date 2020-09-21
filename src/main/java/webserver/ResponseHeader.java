package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;

public class ResponseHeader {

    private static final Logger log = LoggerFactory.getLogger(ResponseHeader.class);

    private final Map<String, String> responseHeader;

    public ResponseHeader(Map<String, String> responseHeader) {
        this.responseHeader = responseHeader;
    }

    public void setHeader(String key, String value) {
        responseHeader.put(key, value);
    }

    public void write(DataOutputStream dataOutputStream) {
        responseHeader.forEach((key, value) -> {
            try {
                dataOutputStream.writeBytes(key + ": " + value + StringUtils.SPACE + System.lineSeparator());
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        });
    }
}
