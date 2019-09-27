package webserver.response;

import webserver.request.HttpRequest;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpResponse that = (HttpResponse) o;
        return Objects.equals(statusLine, that.statusLine) &&
                Objects.equals(header, that.header) &&
                Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusLine, header, body);
    }
}
