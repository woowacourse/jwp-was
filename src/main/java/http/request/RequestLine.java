package http.request;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestLine {
    private final RequestMethod method;
    private final String path;
    private final String httpVersion;

    public RequestLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        if (line == null || line.isEmpty()) {
            throw new IllegalArgumentException("Line is empty.");
        }
        String[] token = line.split(" ");
        this.method = RequestMethod.of(token[0]);
        this.path = token[1];
        this.httpVersion = token[2];
    }

    public boolean isMethodEqualTo(RequestMethod requestMethod) {
        return this.method.equals(requestMethod);
    }

    public boolean isPathEqualTo(String path) {
        return this.path.equals(path);
    }

    public boolean isHttpVersionEqualTo(String httpVersion) {
        return this.httpVersion.equals(httpVersion);
    }

    public String getPath() {
        return path;
    }

    public RequestMethod getMethod() {
        return method;
    }
}
