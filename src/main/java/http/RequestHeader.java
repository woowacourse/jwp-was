package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.exception.NotFoundHeaderException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestHeader {
    public static final String HTTP_METHOD = "method";
    public static final String URL = "url";
    public static final String HTTP_VERSION = "httpVersion";
    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);
    private static final String DELIMITER_OF_START_LINE = " ";
    private static final String DELIMITER_OF_HEADER = ":";
    private static final String DELIMITER_OF_HTTP_MESSAGE = "\n";
    private static final String DELIMITER_OF_QUERY_PARAMETERS = "&";
    private static final String DELIMITER_OF_QUERY_PARAMETER = "=";
    private static final String START_POINT_OF_QUERY_PARAMETERS = "?";

    private final Map<String, String> headers = new HashMap<>();

    public RequestHeader(String headerInput) {
        String[] splitedInput = headerInput.split(DELIMITER_OF_HTTP_MESSAGE);
        splitStartLine(splitedInput[0]);
        splitRequestHeader(splitedInput);
    }

    private void splitStartLine(String startLine) {
        headers.put(HTTP_METHOD, startLine.split(DELIMITER_OF_START_LINE)[0]);
        headers.put(URL, startLine.split(DELIMITER_OF_START_LINE)[1]);
        headers.put(HTTP_VERSION, startLine.split(DELIMITER_OF_START_LINE)[2]);
    }

    private void splitRequestHeader(String[] splitedInput) {
        for (int i = 1; i < splitedInput.length; i++) {
            int indexOfDelimiter = splitedInput[i].indexOf(DELIMITER_OF_HEADER);
            String key = splitedInput[i].substring(0, indexOfDelimiter).trim().toLowerCase();
            String value = splitedInput[i].substring(indexOfDelimiter + 1).trim();

            headers.put(key, value);
        }
    }

    public String get(String key) {
        return Optional.ofNullable(headers.get(key.toLowerCase())).orElseThrow(NotFoundHeaderException::new);
    }

    public Map<String, String> extractQueryParameter() {
        Map<String, String> map = new HashMap<>();
        String url = headers.get(URL);
        logger.debug("Extract Query Parameter in {}", url);

        if (url.contains(START_POINT_OF_QUERY_PARAMETERS)) {
            String query = url.substring(url.indexOf(START_POINT_OF_QUERY_PARAMETERS) + 1);
            String[] queryParameters = query.split(DELIMITER_OF_QUERY_PARAMETERS);

            splitQueryParameter(map, queryParameters);
        }

        return map;
    }

    private void splitQueryParameter(Map<String, String> map, String[] queryParameters) {
        for (String queryParameter : queryParameters) {
            String name = queryParameter.split(DELIMITER_OF_QUERY_PARAMETER)[0];
            String value = queryParameter.split(DELIMITER_OF_QUERY_PARAMETER)[1];

            map.put(name, value);
        }
    }
}
