package webserver.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import webserver.http.exception.InvalidHttpRequestException;

public class HttpElementExtractor {
    private static final String HEADER_KEY_AND_VALUE_DELIMITER = ": ";
    private static final String DATA_DIVIDER = "&";
    private static final String KEY_VALUE_DIVIDER = "=";

    public static String extractRequestLine(BufferedReader bufferedReader) throws IOException {
        String requestLine = bufferedReader.readLine();
        if (requestLine == null) {
            throw new InvalidHttpRequestException("request line이 없습니다.");
        }

        return requestLine;
    }

    public static Map<String, String> extractHeaders(BufferedReader bufferedReader) throws IOException {
        Map<String, String> headers = new LinkedHashMap<>();
        String line;
        line = bufferedReader.readLine();
        String[] headerKeyAndValue = line.split(HEADER_KEY_AND_VALUE_DELIMITER);
        headers.put(headerKeyAndValue[0], headerKeyAndValue[1]);

        while (!(isEmpty(line))) {
            headerKeyAndValue = line.split(HEADER_KEY_AND_VALUE_DELIMITER);
            headers.put(headerKeyAndValue[0], headerKeyAndValue[1]);
            line = bufferedReader.readLine();
        }

        return headers;
    }

    private static boolean isEmpty(String line) {
        return line == null || "".equals(line);
    }

    public static Map<String, String> extractBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        Map<String, String> body = new LinkedHashMap<>();
        String content = IOUtils.readData(bufferedReader, contentLength);
        String[] splitBodies = content.split(DATA_DIVIDER);

        for (String splitBody : splitBodies) {
            String[] bodyKeyAndValue = splitBody.split(KEY_VALUE_DIVIDER);
            body.put(bodyKeyAndValue[0], bodyKeyAndValue[1]);
        }

        return body;
    }
}
