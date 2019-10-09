package webserver.message.response;

public class ResponseBody {
    private byte[] body = new byte[0];

    ResponseBody() {
    }

    ResponseBody(final byte[] body) {
        this.body = body;
    }

    ResponseBody(final String body) {
        this.body = body.getBytes();
    }

    protected byte[] getBody() {
        return body;
    }

    protected int length() {
        return this.body.length;
    }
}
