package web;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestUri {
    private HttpMethod method;
    private RequestPath path;
    private String protocol;

    public RequestUri(BufferedReader bufferedReader) throws IOException {
        String uri = bufferedReader.readLine();
        if (uri.isEmpty()) {
            return;
        }
        String[] splitUri = uri.split(" ");
        this.method = HttpMethod.valueOf(splitUri[0]);
        this.path = new RequestPath(splitUri[1]);
        this.protocol = splitUri[2];
    }

    public String getParam(final String key) {
        return this.path.getRequestParameter(key);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getRequestPath() {
        return path.getRequestPath();
    }

    public String getProtocol() {
        return protocol;
    }
}