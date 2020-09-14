package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exception.RequestHeaderCreateFailException;
import utils.IOUtils;

public class RequestHeader {
    private static final String HEADER_SEPERATOR = ": ";
    private static final int SPLIT_SIZE = 2;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final String DEFAUlT_VALUE = "";

    private Map<String, String> headers;

    private RequestHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public static RequestHeader from(BufferedReader bufferedReader) {
        Map<String, String> headers = new HashMap<>();

        try {
            extractHeaders(bufferedReader, headers);
        } catch (IOException e) {
            throw new RequestHeaderCreateFailException();
        }

        return new RequestHeader(headers);
    }

    private static void extractHeaders(BufferedReader bufferedReader, Map<String, String> headers) throws IOException {
        List<String> lines = IOUtils.readDataUntilEmpty(bufferedReader);
        for (String line : lines) {
            String[] keyValues = line.split(HEADER_SEPERATOR, SPLIT_SIZE);
            headers.put(keyValues[KEY_INDEX], keyValues[VALUE_INDEX]);
        }
    }

    public String findOrEmpty(String input) {
        return headers.getOrDefault(input, DEFAUlT_VALUE);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
