package http.response;

public class Response {
    private StatusLine statusLine;
    private ResponseHeader responseHeader;
    private ResponseBody responseBody;

    public Response(StatusLine statusLine, ResponseHeader responseHeader) {
        this.statusLine = statusLine;
        this.responseHeader = responseHeader;
    }

    public void setStatusCode(int statusCode) {
        statusLine.setStatusCode(statusCode);
    }

    public void setReasonPhrase(String reasonPhrase) {
        statusLine.setReasonPhrase(reasonPhrase);
    }

    public void setLocation(String location) {
        responseHeader.setLocation(location);
    }

    public void setContentType(String type) {
        responseHeader.setType(type);
    }

    public void setResponseBody(byte[] responseBody) {
        this.responseBody = new ResponseBody(responseBody);
    }

    public int getContentLength() {
        return responseBody.getBody().length;
    }

    public String getContentType() {
        return responseHeader.getType();
    }

    public boolean isOk() {
        return statusLine.isOk();
    }

    public String getHttpVersion() {
        return statusLine.getHttpVersion();
    }

    public int getStatusCode() {
        return statusLine.getStatusCode();
    }

    public String getReasonPhrase() {
        return statusLine.getReasonPhrase();
    }

    public byte[] getContentBody() {
        return responseBody.getBody();
    }

    public String getLocation() {
        return responseHeader.getLocation();
    }
}
