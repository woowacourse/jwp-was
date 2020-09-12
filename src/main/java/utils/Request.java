package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Request {

    private Headers headers;
    private String body;

    public Request(List<String> lines, BufferedReader bufferedReader) throws IOException {

        headers = new Headers(lines);
        parseBody(bufferedReader);
    }

    private void parseBody(BufferedReader bufferedReader) throws IOException {

        int contentLength = Optional.ofNullable(getHeader("Content-Length"))
            .map(x -> (String) x)
            .map(Integer::parseInt)
            .orElse(0);

        this.body = IOUtils.readData(bufferedReader, contentLength);
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
        return body;
    }

}
