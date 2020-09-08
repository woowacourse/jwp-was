package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {

    private RequestUri requestUri;
    private Map<String, String> headers = new HashMap<>();

    public RequestHeader(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        if (this.requestUri == null) {
            this.requestUri = new RequestUri(line);
        }
        while (!isEmpty(line)) {
            line = bufferedReader.readLine();
            if (isEmpty(line)) {
                break;
            }
            String[] tokens = line.split(": ");
            headers.put(tokens[0], tokens[1]);
        }
    }

    private boolean isEmpty(String line) {
        return line == null || "".equals(line);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getMethod() {
        return requestUri.method;
    }

    public String getPath() {
        return requestUri.path.getRequestPath();
    }

    public String getProtocol() {
        return requestUri.protocol;
    }

    private static class RequestUri {

        private String method;
        private RequestPath path;
        private String protocol;

        public RequestUri(String uri) {
            if (uri.isEmpty()) {
                return;
            }
            String[] splittedUri = uri.split(" ");
            this.method = splittedUri[0];
            this.path = new RequestPath(splittedUri[1]);
            this.protocol = splittedUri[2];
        }
    }
}
