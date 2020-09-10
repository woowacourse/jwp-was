package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpHeaders {
    private static final String HEADER_KEY_AND_VALUE_DELIMITER = ": ";
    private Map<String, String> headers = new LinkedHashMap<>();

    public HttpHeaders(BufferedReader bufferedReader) throws IOException {
        String line;
        line = bufferedReader.readLine();
        String[] headerKeyAndValue = line.split(HEADER_KEY_AND_VALUE_DELIMITER);
        headers.put(headerKeyAndValue[0], headerKeyAndValue[1]);

        while (!isEmpty(line)) {
            headerKeyAndValue = line.split(HEADER_KEY_AND_VALUE_DELIMITER);
            headers.put(headerKeyAndValue[0], headerKeyAndValue[1]);
            line = bufferedReader.readLine();
        }
    }

    private boolean isEmpty(String line) {
        return "".equals(line) || line == null;
    }

    public String getValue(String key) {
        if (!headers.containsKey(key)) {
            throw new IllegalArgumentException("해당 key의 header 값이 없습니다.");
        }
        return headers.get(key);
    }
}
