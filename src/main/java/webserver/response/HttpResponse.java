package webserver.response;

import webserver.request.HttpRequest;

public class HttpResponse {
    private ResponseStatusLine statusLine;
    private ResponseHeader header;
    private ResponseBody body;

    public HttpResponse() {
        this.header = new ResponseHeader();
        this.body = new ResponseBody();
    }

    public boolean addBody(byte[] body) {
        return this.body.addBody(body);
    }

    public void addStatusLine(HttpRequest httpRequest, String statusCode, String statusText) {
        statusLine = ResponseStatusLine.of(httpRequest, statusCode, statusText);
    }

    public boolean addHeader(String key, String value) {
        return header.addAttribute(key, value);
    }

    public String responseLine() {
        return statusLine.response();
    }

    public String responseHeader() {
        return header.response();
    }

    public ResponseHeader getHeader() {
        return header;
    }

    public ResponseBody getBody() {
        return body;
    }
}
