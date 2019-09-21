package webserver.domain.response;

public class ResponseBody {
    private byte[] body = new byte[0];

    ResponseBody() {}

    ResponseBody(final byte[] body) {
        this.body = body;
    }

    ResponseBody(final String body) {
        this.body = body.getBytes();
    }

    public byte[] getBody() {
        return body;
    }

    public int length() {
        return this.body.length;
    }
}
