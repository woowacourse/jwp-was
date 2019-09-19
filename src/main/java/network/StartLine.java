package network;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class StartLine {
    private static final int CONDITION_FOR_EXIST = 2;
    private static final String QUERY_PARAM_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String BLANK = " ";

    private HttpMethod httpMethod;
    private Url url;
    private Map<String, String> parameters;

    private StartLine(final HttpMethod httpMethod, final Url url, final Map<String, String> parameters) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.parameters = parameters;
    }

    public static StartLine of(final String line) {
        String[] firstLine = line.split(BLANK);
        String[] tokens = firstLine[1].split("\\?");

        return new StartLine(HttpMethod.valueOf(firstLine[0]), new Url(tokens[0]), extractQueryParams(tokens));
    }

    private static Map<String, String> extractQueryParams(final String[] tokens) {
        if (tokens.length == CONDITION_FOR_EXIST) {
            return generateQueryParams(tokens[1]);
        }
        return Collections.emptyMap();
    }

    private static Map<String, String> generateQueryParams(final String queryParam) {
        return Arrays.stream(queryParam.split(QUERY_PARAM_DELIMITER))
                .map(param -> param.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(tokens -> tokens[0], tokens -> tokens[1]));
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url.getFullUrl();
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "StartLine{" +
                "httpMethod=" + httpMethod +
                ", url='" + url + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
