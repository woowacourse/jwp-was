package webserver.domain;

public class ResponseBody {
    private byte[] body = new byte[0];

    ResponseBody() {}

    ResponseBody(final byte[] body) {
        this.body = body;
    }

    public byte[] getBytes() {
        return body;
    }

    public int length() {
        return this.body.length;
    }
}
