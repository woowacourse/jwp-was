package http.response;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponseLine {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String BLANK = " ";
    private static final String HTTP_VERSION = "HTTP/1.1";

    private final String version;
    private HttpStatus httpStatus;

    public HttpResponseLine() {
        this.version = HTTP_VERSION;
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public String getVersion() {
        return version;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void write(final DataOutputStream dos) throws IOException {
        dos.writeBytes(version + BLANK + httpStatus.getCode() + BLANK + httpStatus + NEW_LINE);
    }
}
