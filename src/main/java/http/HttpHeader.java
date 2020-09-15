package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import exception.RequestHeaderCreateFailException;
import utils.IOUtils;

public class HttpHeader {
    private static final String HEADER_SEPERATOR = ": ";
    private static final int SPLIT_SIZE = 2;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final String DEFAUlT_VALUE = "";
    private static final String JOIN_SEPERATOR = ",";

    private SortedMap<String, String> headers = new TreeMap<>(Collections.reverseOrder());

    public HttpHeader() {
    }

    private HttpHeader(Map<String, String> headers) {
        this.headers.putAll(headers);
    }

    public static HttpHeader empty() {
        return new HttpHeader();
    }

    public static HttpHeader from(BufferedReader bufferedReader) {
        try {
            return new HttpHeader(extractHeaders(bufferedReader));
        } catch (IOException e) {
            throw new RequestHeaderCreateFailException();
        }
    }

    private static Map<String, String> extractHeaders(BufferedReader bufferedReader) throws IOException {
        List<String> lines = IOUtils.readDataUntilEmpty(bufferedReader);

        Map<String, String> headers = new HashMap<>();
        for (String line : lines) {
            String[] keyValues = line.split(HEADER_SEPERATOR, SPLIT_SIZE);
            headers.put(keyValues[KEY_INDEX], keyValues[VALUE_INDEX]);
        }
        return headers;
    }

    public void addHeader(String key, String value) {
        if (headers.containsKey(key)) {
            String old = headers.get(key);
            headers.replace(key, old + JOIN_SEPERATOR + value);
        } else {
            headers.put(key, value);
        }
    }

    public String findOrEmpty(String input) {
        return headers.getOrDefault(input, DEFAUlT_VALUE);
    }

    public String convertToString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            stringBuilder.append(entry.getKey() + HEADER_SEPERATOR + entry.getValue());
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
