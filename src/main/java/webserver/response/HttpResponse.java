package webserver.response;

import webserver.HttpStatus;
import webserver.request.HttpRequest;

import java.util.Objects;

public class HttpResponse {
    private ResponseStatusLine responseStatusLine;
    private ResponseHeader responseHeader;
    private ResponseBody responseBody;

    public HttpResponse() {
        this.responseHeader = new ResponseHeader();
        this.responseBody = new ResponseBody();
    }

    public boolean addBody(byte[] body) {
        return this.responseBody.addBody(body);
    }

    public void addStatusLine(HttpRequest httpRequest, HttpStatus httpStatus) {
        responseStatusLine = ResponseStatusLine.of(httpRequest, httpStatus);
    }

    public boolean addHeader(String key, String value) {
        return responseHeader.addAttribute(key, value);
    }

    public String responseLine() {
        return responseStatusLine.response();
    }

    public String responseHeader() {
        return responseHeader.response();
    }

    public ResponseBody getResponseBody() {
        return responseBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpResponse that = (HttpResponse) o;
        return Objects.equals(responseStatusLine, that.responseStatusLine) &&
                Objects.equals(responseHeader, that.responseHeader) &&
                Objects.equals(responseBody, that.responseBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseStatusLine, responseHeader, responseBody);
    }
}
