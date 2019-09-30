package webserver.http.response;

public class ResponseBody {
    private byte[] body;

    public byte[] getBody() {
        return this.body;
    }

    public void setBody(byte[] responseBody) {
        this.body = responseBody;
    }
}
