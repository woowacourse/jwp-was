package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class Request {

    private final Headers headers;
    private final Body body;
    private final AcceptType type;

    public Request(List<String> lines, BufferedReader bufferedReader) throws IOException {
        headers = new Headers(lines);
        body = new Body(bufferedReader, headers.getHeader("Content-Length"));
        type = AcceptType.of((String) headers.getHeader("filePath"));
    }

    public boolean isGet() {
        return headers.isGetRequest();
    }

    public boolean isPost() {
        return headers.isPostRequest();
    }

    public Object getHeader(String name) {
        return headers.getHeader(name);
    }

    public AcceptType getType() {
        return type;
    }

    public <T> T getBody(Class<T> type) {
        return body.bodyMapper(type);
    }

    public Body getBody() {
        return body;
    }
}
