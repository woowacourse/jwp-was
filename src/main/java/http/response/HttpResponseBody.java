package http.response;

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
}
