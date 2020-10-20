package web.response;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseLine {
    private final ResponseStatus responseStatus;
    private final String version;

    public ResponseLine(ResponseStatus responseStatus, String version) {
        this.responseStatus = responseStatus;
        this.version = version;
    }

    @Override
    public String toString() {
        return version + " " + responseStatus;
    }

    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeBytes(this.toString() + " \r\n");
    }
}
