package webserver.http.response;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseBody {
    private byte[] body;

    public ResponseBody(byte[] body) {
        this.body = body;
    }

    public ResponseBody(String body) {
        this(body.getBytes());
    }

    public byte[] getBody() {
        return body;
    }

    public int getBodyLength() {
        return body.length;
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.write(body, 0, getBodyLength());
    }
}
