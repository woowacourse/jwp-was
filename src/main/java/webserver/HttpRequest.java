package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private final HttpMethod httpMethod;
    private final String resourcePath;

    public HttpRequest(HttpMethod httpMethod, String resourcePath) {
        this.httpMethod = httpMethod;
        this.resourcePath = resourcePath;
    }

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        String[] requestLine = bufferedReader.readLine().split(" ");
        this.httpMethod = HttpMethod.valueOf(requestLine[0]);
        this.resourcePath = requestLine[1];
    }

    public HttpRequest(InputStream inputStream) throws IOException {
        this(new BufferedReader(new InputStreamReader(inputStream)));
    }

    public boolean isResourcePath(String resourcePath) {
        return this.resourcePath.equals(resourcePath);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }
}
