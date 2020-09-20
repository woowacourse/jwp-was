package webserver.http.response;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseStatusLine {
    private String httpVersion;
    private HttpStatus httpStatus;

    public ResponseStatusLine(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeBytes(
            httpVersion + " " + httpStatus.getStatusCode() + " " + httpStatus.getStatus() + System.lineSeparator());
    }
}
