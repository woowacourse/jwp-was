package http.response;


public class Response {

    private ResponseStatus responseStatus;
    private ResponseHeaders responseHeaders = new ResponseHeaders();
    private ResponseBody responseBody = new ResponseBody();

    public Response() {
    }

    public void addResponseHeaders(String key, String value) {
        this.responseHeaders.addResponseHeaders(key, value);
    }

    public byte[] createBytes() {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1");
        sb.append(" ");
        sb.append(responseStatus.getStatusCode());
        sb.append(" ");
        sb.append(responseStatus.getStatusMessage());
        sb.append("\r\n");

        responseHeaders.append(sb);
        sb.append("\r\n");
        if (responseBody.hasBody()) {
            sb.append(new String(responseBody.getBody()));
        }

        return sb.toString().getBytes();
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public ResponseHeaders getResponseHeaders() {
        return responseHeaders;
    }

    public ResponseBody getResponseBody() {
        return responseBody;
    }

    public Response ok() {
        this.responseStatus = ResponseStatus.OK;
        return this;
    }

    public Response found() {
        this.responseStatus = ResponseStatus.FOUND;
        return this;
    }

    public Response putResponseHeaders(String key, String value) {
        this.responseHeaders.addResponseHeaders(key, value);
        return this;
    }

    public Response body(byte[] body) {
        this.responseBody = new ResponseBody(body);
        this.addResponseHeaders("Content-Length: ", String.valueOf(body.length));
        return this;
    }
}
