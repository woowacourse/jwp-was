package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private String method;
    private String path;
    private String httpVersion;

    private Map<String, String> params;

    public RequestHeader(BufferedReader br) throws IOException {
        params = new HashMap<>();
        String line = br.readLine();
        if (line.isEmpty()) {
            throw new IllegalArgumentException();
        }
        String[] token = line.split(" ");
        this.method = token[0];
        this.path = token[1];
        this.httpVersion = token[2];

        line = br.readLine();
        while ((line != null) && !"".equals(line)) {
            token = line.split(": ");
            params.put(token[0], token[1]);
            line = br.readLine();
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public int getContentLength() {
        if (this.params.get("Content-Length") == null) {
            return 0;
        }
        return Integer.parseInt(this.params.get("Content-Length"));
    }
}
