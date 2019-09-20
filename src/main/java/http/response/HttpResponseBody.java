package http.response;

public class HttpResponseBody {
    private String contentsType;
    private byte[] body;

    public HttpResponseBody(byte[] body, String contentsType) {

        this.body = body;
        this.contentsType = contentsType;
    }

    public String getContentsType() {
        return contentsType;
    }

    public byte[] getBody() {
        return body;
    }

    public int getBodyLength() {
        return body.length;
    }
}
