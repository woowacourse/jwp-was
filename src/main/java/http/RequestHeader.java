package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private Map<String, String> requestHeaders = new HashMap<>();

    public RequestHeader(BufferedReader br) throws IOException {
        String line = br.readLine();

        while (line != null && !"".equals(line)) {
            String[] token = line.split(": ");
            this.requestHeaders.put(token[0], token[1]);
            line = br.readLine();
        }
    }

    public Integer getContentLength() {
        if (requestHeaders.get("Content-Length") == null) {
            return 0;
        }
        return Integer.parseInt(requestHeaders.get("Content-Length"));
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }
}
