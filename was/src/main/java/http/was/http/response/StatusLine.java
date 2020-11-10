package http.was.http.response;

import java.io.DataOutputStream;
import java.io.IOException;

public class StatusLine {
    private final String httpVersion;
    private final Status status;

    public StatusLine(String httpVersion, Status status) {
        this.httpVersion = httpVersion;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeBytes(this.toString() + " " + System.lineSeparator());
    }

    @Override
    public String toString() {
        return httpVersion + " " + status;
    }
}
