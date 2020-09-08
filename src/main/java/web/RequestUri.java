package web;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestUri {
    private String method;
    private RequestPath path;
    private String protocol;

    public RequestUri(BufferedReader bufferedReader) throws IOException {
        String uri = bufferedReader.readLine();
        if (uri.isEmpty()) {
            return;
        }
        String[] splittedUri = uri.split(" ");
        this.method = splittedUri[0];
        this.path = new RequestPath(splittedUri[1]);
        this.protocol = splittedUri[2];
    }

    public String getParam(final String key) {
        return this.path.getRequestParameter(key);
    }

    public String getMethod() {
        return method;
    }

    public RequestPath getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }
}