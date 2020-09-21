package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import utils.StringUtils;

public class StatusLine {

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
            throw new IllegalArgumentException();
        }
    }

    public void write(DataOutputStream dataOutputStream) throws IOException {
        httpVersion.write(dataOutputStream);
        statusCode.write(dataOutputStream);
        dataOutputStream.writeBytes(System.lineSeparator());
    }
}
