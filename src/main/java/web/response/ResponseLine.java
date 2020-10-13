package web.response;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseLine {
    private final Status status;
    private final String version;

    public ResponseLine(Status status, String version) {
        this.status = status;
        this.version = version;
    }

    @Override
    public String toString() {
        return version + " " + status;
    }

    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeBytes(this.toString() + " \r\n");
    }
}
