package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestHeader {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestHeader.class);
    private static final String DELIMITER_OF_HEADER = ":";
    private static final String DELIMITER_OF_QUERY_PARAMETERS = "&";
    private static final String DELIMITER_OF_QUERY_PARAMETER = "=";
    private static final String START_POINT_OF_QUERY_PARAMETERS = "?";

    private final HttpRequestStartLine httpRequestStartLine;
    private final Map<String, String> headers = new HashMap<>();

    public HttpRequestHeader(List<String> inputs) {
        logger.debug("Show inputs : {}", inputs);
        httpRequestStartLine = new HttpRequestStartLine(inputs.get(0));
        for (String header : inputs.subList(1, inputs.size())) {
            int indexOfDelimiter = header.indexOf(DELIMITER_OF_HEADER);
            String key = header.substring(0, indexOfDelimiter).trim().toLowerCase();
            String value = header.substring(indexOfDelimiter + 1).trim();

            headers.put(key, value);
        }
    }

    public String get(String key) {
        return headers.get(key.toLowerCase());
    }

    public Map<String, String> extractQueryParameter() {
        Map<String, String> map = new HashMap<>();
        String url = httpRequestStartLine.getUrl();
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

    public String getMethod() {
        return httpRequestStartLine.getMethod();
    }

    public String getUrl() {
        return httpRequestStartLine.getUrl();
    }

    public String getVersion() {
        return httpRequestStartLine.getVersion();
    }
}
