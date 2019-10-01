package webserver.http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.Pair;
import webserver.http.utils.HttpUtils;
import webserver.http.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Parameters {
    private static final Logger log = LoggerFactory.getLogger(Parameters.class);

    private static final String DELIMITER_QUERY = "&";
    private static final String DELIMITER_QUERY_PAIR = "=";

    private final Map<String, String> parameters;

    public Parameters() {
        this.parameters = new HashMap<>();
    }

    Parameters(final String parameters) {
        this.parameters = parseToMap(parameters);
    }

    private static Map<String, String> parseToMap(final String queryParamsText) {
        if (StringUtils.isEmpty(queryParamsText)) {
            return new HashMap<>();
        }

        try {
            final String queryParams = URLDecoder.decode(queryParamsText, StandardCharsets.UTF_8.toString());
            return Arrays.stream(queryParams.split(DELIMITER_QUERY))
                    .map(queryParam -> HttpUtils.parseKeyValue(queryParam, DELIMITER_QUERY_PAIR))
                    .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    void addByString(final String parametersText) {
        addAll(parseToMap(parametersText));
    }

    void add(final String key, final String value) {
        parameters.put(key, value);
    }

    void addAll(final Map<String, String> map) {
        parameters.putAll(map);
    }

    String get(final String key) {
        return parameters.get(key);
    }

    int size() {
        return parameters.size();
    }

}
