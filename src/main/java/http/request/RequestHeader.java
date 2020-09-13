package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.IOUtils;

public class RequestHeader {
    private Map<String, String> headers;

    private RequestHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public static RequestHeader from(BufferedReader bufferedReader) {
        Map<String, String> headers = new HashMap<>();

        try {
            extractHeaders(bufferedReader, headers);
        } catch (IOException e) {
            throw new RuntimeException("IO EXCEPTION 발생");
        }

        return new RequestHeader(headers);
    }

    private static void extractHeaders(BufferedReader bufferedReader, Map<String, String> headers) throws IOException {
        List<String> lines = IOUtils.readDataUntilEmpty(bufferedReader);
        for (String line : lines) {
            String[] keyValues = line.split(": ", 2);
            headers.put(keyValues[0], keyValues[1]);
        }
    }

    public String findOrEmpty(String input) {
        return headers.getOrDefault(input, "");
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
