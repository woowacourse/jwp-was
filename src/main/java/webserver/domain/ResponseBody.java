package webserver.domain;

import java.nio.charset.StandardCharsets;

public class ResponseBody {
    private byte[] body = new byte[0];

    ResponseBody() {}

    ResponseBody(final byte[] body) {
        this.body = body;
    }

    ResponseBody(final String body) {
        this.body = body.getBytes(StandardCharsets.UTF_8);
    }

    public byte[] getBody() {
        return body;
    }

    public int length() {
        return this.body.length;
    }
}
