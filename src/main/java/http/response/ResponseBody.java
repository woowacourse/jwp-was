package http.response;

public class ResponseBody {
    private byte[] body;

    private ResponseBody(byte[] body) {
        this.body = body;
    }

    public static ResponseBody of() {
        return new ResponseBody(new byte[0]);
    }

    protected static ResponseBody of(byte[] body) {
        return new ResponseBody(body);
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public String toString() {
        return new String(body);
    }
}
