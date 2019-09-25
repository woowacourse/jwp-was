package http.response;


public class Response {

    private ResponseStatus responseStatus;
    private ResponseHeaders responseHeaders;
    private ResponseBody responseBody;


    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public void setResponseHeaders(ResponseHeaders responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public void addResponseHeaders(String key, String value) {
        this.responseHeaders.addResponseHeaders(key, value);
    }

    public void setResponseBody(byte[] body) {
        this.responseBody = new ResponseBody(body);
        this.responseHeaders.addResponseHeaders("Content-Length: ", String.valueOf(body.length));
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
        System.out.println("hello");
        return sb.toString().getBytes();
    }

    public void setEmptyResponseBody() {
        this.responseBody = new ResponseBody(null);
    }
}
