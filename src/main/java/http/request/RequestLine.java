package http.request;

import http.exception.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestLine {
    private static final Logger logger = LoggerFactory.getLogger(RequestLine.class);
    private static final String DELIMITER_OF_REQUEST_LINE = " ";
    private static final String START_POINT_OF_QUERY_PARAMETERS = "?";
    private static final String DELIMITER_OF_QUERY_PARAMETERS = "&";
    private static final String DELIMITER_OF_QUERY_PARAMETER = "=";
    private static final int KEY = 0;
    private static final int VALUE = 1;
    private static final int REQUEST_LINE_SIZE = 3;

    private final String method;
    private final String url;
    private final String httpVersion;

    public RequestLine(String requestLine) {
        String[] splitedRequestLine = validateRequestLine(requestLine);
        method = splitedRequestLine[0];
        url = splitedRequestLine[1];
        httpVersion = splitedRequestLine[2];
    }

    private static String[] validateRequestLine(String requestLine) {
        String[] requestLineData = requestLine.split(DELIMITER_OF_REQUEST_LINE);

        if (requestLineData.length != REQUEST_LINE_SIZE) {
            throw new InvalidRequestException("Invalid Request Line");
        }

        return requestLineData;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public boolean isGet() {
        return "GET".equals(method);
    }

    public boolean isPost() {
        return "POST".equals(method);
    }

    public Map<String, String> extractQueryParameter() {
        logger.debug("Extract Query Parameter in {}", url);

        if (!url.contains(START_POINT_OF_QUERY_PARAMETERS)) {
            return Collections.emptyMap();
        }

        String query = url.substring(url.indexOf(START_POINT_OF_QUERY_PARAMETERS) + 1);
        String[] queryParameters = query.split(DELIMITER_OF_QUERY_PARAMETERS);

        return splitQueryParameter(queryParameters);
    }

    private Map<String, String> splitQueryParameter(String[] queryParameters) {
        Map<String, String> map = new HashMap<>();

        for (String queryParameter : queryParameters) {
            String[] splitedQueryParameter = queryParameter.split(DELIMITER_OF_QUERY_PARAMETER);
            String key = splitedQueryParameter[KEY];
            String value = splitedQueryParameter[VALUE];

            map.put(key, value);
        }

        return map;
    }
}
