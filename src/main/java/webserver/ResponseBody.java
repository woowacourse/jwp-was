package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseBody {
    private static final Logger log = LoggerFactory.getLogger(ResponseHeader.class);

    private final byte[] body;

    public ResponseBody(byte[] body) {
        this.body = body;
    }

    public void write(DataOutputStream dataOutputStream) {
        if (Objects.isNull(body)) {
            return;
        }
        writeAndFlush(dataOutputStream);
    }

    private void writeAndFlush(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public int getContentLength() {
        return body.length;
    }
}
