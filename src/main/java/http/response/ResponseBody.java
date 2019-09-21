package http.response;

public class ResponseBody {

    private byte[] body;

    public ResponseBody(final byte[] body) {
        this.body = body;
    }

    public int getLength() {
        return body.length;
    }

    public byte[] getBody() {
        return this.body;
    }
}
