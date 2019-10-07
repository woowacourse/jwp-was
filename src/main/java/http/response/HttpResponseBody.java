package http.response;

public class HttpResponseBody {
    private byte[] body = new byte[]{};

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public int getLength() {
        return body.length;
    }
}
