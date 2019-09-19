package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpHeader {
    private Map<String, String> headers;

    private HttpHeader() {
        this.headers = new HashMap<>();
    }

    public static HttpHeader of(final BufferedReader bufferedReader) throws IOException {
        HttpHeader httpHeader = new HttpHeader();
        String line = bufferedReader.readLine();

        while (!line.equals("")) {
            String[] headerLine = bufferedReader.readLine().split(": ");
            if (headerLine.length == 1) {
                break;
            }
            httpHeader.addHeader(headerLine[0], headerLine[1]);
        }

        return httpHeader;
    }

    private void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String get(final String key) {
        return headers.get(key);
    }

    @Override
    public String toString() {
        return "HttpHeader{" +
                "headers=" + headers +
                '}';
    }
}
