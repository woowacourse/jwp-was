package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestUrl {
    private static final Logger logger = LoggerFactory.getLogger(RequestUrl.class);
    private static final String START_POINT_OF_QUERY_PARAMETERS = "?";
    private static final String DELIMITER_OF_QUERY_STRING = "&";
    private static final String DELIMITER_OF_QUERY_PARAMETER = "=";

    private final String url;
    private final Map<String, String> queryParameters;

    public RequestUrl(String url) {
        this.url = url;
        queryParameters = extractQueryParameters();
    }

    private Map<String, String> extractQueryParameters() {
        Map<String, String> map = new HashMap<>();
        logger.debug("Extract Query Parameter in {}", url);

        if (url.contains(START_POINT_OF_QUERY_PARAMETERS)) {
            String query = url.substring(url.indexOf(START_POINT_OF_QUERY_PARAMETERS) + 1);
            String[] queryParameters = query.split(DELIMITER_OF_QUERY_STRING);

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

    public Map<String, String> getQueryParameters() {
        return Collections.unmodifiableMap(queryParameters);
    }
}
