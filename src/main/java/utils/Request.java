package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class Request {

    private Headers headers;
    private Body body;

    public Request(List<String> lines, BufferedReader bufferedReader) throws IOException {
        headers = new Headers(lines);
        body = new Body(bufferedReader, headers.getHeader("Content-Length"));
    }

    public boolean isGetRequest() {
        return headers.isGetRequest();
    }

    public boolean isPostRequest() {
        return headers.isPostRequest();
    }

    public Object getHeader(String name) {
        return headers.getHeader(name);
    }

    public AcceptType getType() {
        return headers.getType();
    }

    public String getBody() {
        return body.getBody();
    }
}
