package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private final Map<String, String> headers = new HashMap<>();

    public RequestHeader(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        while (!isEmpty(line)) {
            String[] tokens = line.split(": ");
            headers.put(tokens[0], tokens[1]);
            line = bufferedReader.readLine();
        }
    }

    private boolean isEmpty(String line) {
        return line == null || "".equals(line);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
