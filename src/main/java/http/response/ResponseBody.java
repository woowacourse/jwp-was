package http.response;

public class ResponseBody {
    private final byte[] body;

    public ResponseBody(byte[] body) {
        this.body = body;
    }

    public ResponseBody() {
        this.body = "".getBytes();
    }

    public byte[] getContents() {
        return body;
    }
}
