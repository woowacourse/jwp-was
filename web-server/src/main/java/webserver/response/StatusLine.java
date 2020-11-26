package webserver.response;

import java.io.DataOutputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;

public class StatusLine {

    private static final Logger log = LoggerFactory.getLogger(StatusLine.class);

    private final HttpVersion httpVersion;
    private final StatusCode statusCode;

    public StatusLine(String statusLine) {
        validate(statusLine);
        String[] statusLineSegment = statusLine.split(" ");
        this.httpVersion = HttpVersion.find(statusLineSegment[0]);
        this.statusCode = StatusCode.find(statusLineSegment[1]);
    }

    private void validate(String statusLine) {
        if (StringUtils.isBlank(statusLine)) {
            log.error("StatusLine : {} is Null or Blank!", statusLine);
            throw new IllegalArgumentException("StatusLine이 Null 또는 공백입니다!");
        }
    }

    public void write(DataOutputStream dataOutputStream) throws IOException {
        httpVersion.write(dataOutputStream);
        statusCode.write(dataOutputStream);
        dataOutputStream.writeBytes(System.lineSeparator());
    }
}
