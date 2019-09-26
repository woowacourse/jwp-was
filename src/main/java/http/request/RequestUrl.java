package http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestUrl {
    private static final Logger logger = LoggerFactory.getLogger(RequestUrl.class);
    private static final String START_POINT_OF_QUERY_PARAMETERS = "?";
    private static final String DELIMITER_OF_QUERY_STRING = "&";
    private static final String DELIMITER_OF_QUERY_PARAMETER = "=";

    private final String resourcePath;
    private final Map<String, String> queryParameters;

    public RequestUrl(String url) {
        this.resourcePath = resourcePath(url);
        this.queryParameters = extractQueryParameters(url);
    }

    private String resourcePath(String url) {
        String queryString = queryString(url);
        if ("/".equals(queryString)) {
            return "/index.html";
        }
        return queryString;
    }

    private String queryString(String url) {
        if (url.contains("?")) {
            return url.substring(0, url.indexOf("?"));
        }
        return url;
    }

    private Map<String, String> extractQueryParameters(String url) {
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

    String getResourcePath() {
        return resourcePath;
    }

    public String getQueryParameter(String key) {
        return queryParameters.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestUrl that = (RequestUrl) o;
        return Objects.equals(resourcePath, that.resourcePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourcePath);
    }
}
