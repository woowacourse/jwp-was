package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private final Map<String, String> params;

    public RequestHeader(BufferedReader br) throws IOException {
        params = new HashMap<>();
        String line = br.readLine();
        if (line.isEmpty()) {
            throw new IllegalArgumentException("Line is empty.");
        }
        while ((line != null) && !line.isEmpty()) {
            String[] token = line.split(": ");
            params.put(token[0].toLowerCase(), token[1]);
            line = br.readLine();
        }
    }

    public int getContentLength() {
        String contentLength = this.params.get("content-length");
        if (contentLength == null) {
            return 0;
        }
        return Integer.parseInt(contentLength);
    }

    public Map<String, String> getParams() {
        return params;
    }
}
