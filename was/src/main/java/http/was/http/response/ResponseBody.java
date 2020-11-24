package http.was.http.response;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseBody {
    private final byte[] body;

    public ResponseBody(byte[] body) {
        this.body = body;
    }

    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.write(body, 0, body.length);
    }

    public int getContentLength() {
        return body.length;
    }

    public byte[] getBody() {
        return body;
    }
}
