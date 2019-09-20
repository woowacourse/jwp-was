package webserver.httpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeader {
    private final Map<String, String> headers;

    public HttpRequestHeader(Map<String, String> headers) {
        this.headers = new HashMap<>(headers);
    }

    public static HttpRequestHeader of(BufferedReader br) {
        HashMap<String, String> headers = new HashMap<>();
        String line = null;
        try {
            line = br.readLine();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

        while (!"".equals(line) && line != null) {
            String[] attribute = line.split(": ");
            headers.put(attribute[0], attribute[1]);
            try {
                line = br.readLine();
            } catch (IOException e) {
                throw new IllegalArgumentException();
            }
        }
        return new HttpRequestHeader(headers);
    }

    public int getContentLength() {
        return Integer.parseInt(headers.get("Content-Length"));
    }

    public String getHost() {
        return headers.get("Host");
    }

    public String getContentType() {
        return headers.get("Content-Type");
    }
}
