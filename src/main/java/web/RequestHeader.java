package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {

    private String requestURI;
    private Map<String, String> headers = new HashMap<>();

    public RequestHeader(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        if (this.requestURI == null) {
            this.requestURI = line;
        }
        while (!"".equals(line)) {
            line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            String[] tokens = line.split(": ");
            headers.put(tokens[0], tokens[1]);
        }
    }

    public String getRequestURI() {
        return requestURI;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
