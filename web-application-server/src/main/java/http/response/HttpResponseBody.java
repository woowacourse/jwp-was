package http.response;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponseBody {

    private byte[] body;

    public HttpResponseBody() {
        this.body = new byte[0];
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void write(final DataOutputStream dos) throws IOException {
        dos.write(body, 0, body.length);
    }
}
