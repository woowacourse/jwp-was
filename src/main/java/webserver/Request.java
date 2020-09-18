package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class Request {

    private HttpMethod method;
    private String path;
    private String version;
    private final Headers headers;
    private final Body body;
    private final AcceptType type;

    public Request(List<String> lines, BufferedReader bufferedReader) throws IOException {
        parseRequestFirstLine(lines.remove(0));
        headers = new Headers(lines);
        body = new Body(bufferedReader, headers.getHeader("Content-Length"));
        type = AcceptType.of(path);
    }

    private void parseRequestFirstLine(String requestFirstLine) {
        String[] splitRequestFirstLine = requestFirstLine.split(" ");

        this.method = HttpMethod.of(splitRequestFirstLine[0]);
        this.path = splitRequestFirstLine[1].split("\\?")[0];
        this.version = splitRequestFirstLine[2];
    }

    public boolean isGet() {
        return method.isGet();
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
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
