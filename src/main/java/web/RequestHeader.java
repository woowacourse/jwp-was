package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestHeader {
    private static final String DELIMITER = ": ";
    private static final String CONTENT_LENGTH = "Content-Length";

    private final Map<String, String> headers;

    public RequestHeader(BufferedReader br) throws IOException {
        HashMap<String, String> headers = new HashMap<>();
        String line;

        while (!(line = br.readLine()).equals("")) {
            String[] header = line.split(DELIMITER);
            headers.put(header[0], header[1]);
        }

        this.headers = headers;
    }

    public int getContentLength() {
        try {
            return Integer.parseInt(headers.get(CONTENT_LENGTH));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestHeader that = (RequestHeader) o;
        return Objects.equals(headers, that.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }
}
