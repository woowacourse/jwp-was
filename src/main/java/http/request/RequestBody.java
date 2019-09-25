package http.request;

public class RequestBody {
    public static final String FOM_DATA_TYPE = "application/x-www-form-urlencoded";

    private byte[] body;

    private RequestBody(byte[] body) {
        this.body = body;
    }

    public static RequestBody of(byte[] body) {
        return new RequestBody(body);
    }

    public byte[] getBody() {
        return body;
    }

    public String getFormData(RequestHeader requestHeader) {
        if (FOM_DATA_TYPE.equals(requestHeader.getHeader(HttpRequest.CONTENT_TYPE_NAME))) {
            return new String(body);
        }

        return null;
    }
}
