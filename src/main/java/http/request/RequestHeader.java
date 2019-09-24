package http.request;

import http.exception.NotFoundHeaderException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class RequestHeader {
    private static final String DELIMITER_OF_HEADER = ":";
    private static final String DELIMITER_OF_HTTP_HEADER = "\n";

    private final Map<String, String> headers = new HashMap<>();

    public RequestHeader(String requestHeader) {
        Objects.requireNonNull(requestHeader);
        String[] splitedInput = requestHeader.split(DELIMITER_OF_HTTP_HEADER);
        splitRequestHeader(splitedInput);
    }

    private void splitRequestHeader(String[] splitedInput) {
        for (String input : splitedInput) {
            int indexOfDelimiter = input.indexOf(DELIMITER_OF_HEADER);
            String key = input.substring(0, indexOfDelimiter).trim().toLowerCase();
            String value = input.substring(indexOfDelimiter + 1).trim();

            headers.put(key, value);
        }
    }

    public String get(String key) {
        return Optional.ofNullable(headers.get(key.toLowerCase())).orElseThrow(NotFoundHeaderException::new);
    }
}
