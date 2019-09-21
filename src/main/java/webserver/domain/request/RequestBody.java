package webserver.domain.request;

import org.slf4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class RequestBody {
    private static final Logger LOG = getLogger(RequestBody.class);

    private static final String UTF_8 = "UTF-8";
    private static final String QUERY_DELIMITER = "&";
    private static final String PARAMETER_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final String EMPTY = "";

    private final Map<String, String> queries;

    public RequestBody(final String rawQuery) {
        this.queries = extractQueries(rawQuery);
    }

    public void putQueryBy(final String rawQueries) {
        this.queries.putAll(extractQueries(rawQueries));
    }

    private Map<String, String> extractQueries(final String rawQuery) {
        return Arrays.stream(rawQuery.split(QUERY_DELIMITER))
                .map(query -> query.split(PARAMETER_DELIMITER, 2))
                .filter(this::queryFilter)
                .collect(Collectors.toMap(array -> urlDecode(array[KEY_INDEX]), array -> urlDecode(array[VALUE_INDEX])));
    }

    private boolean queryFilter(final String[] keyValue) {
        return keyValue.length == 2 && !keyValue[0].isEmpty() && !keyValue[1].isEmpty();
    }

    private String urlDecode(final String encodedString) {
        try {
            return URLDecoder.decode(encodedString, UTF_8);
        } catch (UnsupportedEncodingException e) {
            LOG.error(e.getMessage());
            return "";
        }
    }

    public Map<String, String> getQueries() {
        return Collections.unmodifiableMap(queries);
    }

    public String getQueryValue(final String key) {
        return queries.getOrDefault(key, EMPTY);
    }
}
