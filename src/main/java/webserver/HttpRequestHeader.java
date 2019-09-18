package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeader {
    private final Map<String, String> headers;

    public HttpRequestHeader(BufferedReader br) throws IOException {
        headers = new HashMap<>();
        String line = br.readLine();
        while (!"".equals(line) && line != null) {
            String[] attribute = line.split(": ");
            headers.put(attribute[0], attribute[1]);
            line = br.readLine();
        }
    }

    public int getContentLength() {
        return Integer.parseInt(headers.get("Content-Length"));
    }

    public String getHost() {
        return headers.get("Host");
    }
}
