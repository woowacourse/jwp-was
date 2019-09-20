package webserver.domain;

import org.slf4j.Logger;
import webserver.view.NetworkInput;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class Request {
    private static final Logger LOG = getLogger(Request.class);
    private static final String KEY_VALUE_DELIMETER = ":";
    private static final String EMPTY = "";
    private static final String CONTENT_LENGTH = "content-length";
    private static final String ZERO_LENGTH = "0";
    private static final String SPACE_DELIMITER = " ";
    private static final String QUERY_DELIMITER = "\\?";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int URL_INDEX = 1;
    private static final int PATH_INDEX = 0;
    private static final int QUERY_INDEX = 0;
    private static final int METHOD_INDEX = 0;
    private static final int PROTOCOL_INDEX = 2;
    private static final int ZERO = 0;
    private static final int NO_QUERY = 1;

    // TODO: 잘못된 요청을 받았을 때 문제가 없는지 검토
    // TODO: 래퍼 클래스로 감싸기
    //private final Path path;
    private final HttpMethod httpMethod;
    private final String path;
    private final String protocol;
    private final String body;
    private final QueryParameter queryParameter;
    private final Map<String, String> requestFields;

    public Request(final NetworkInput networkInput) throws IOException {
        final String[] httpMethodAndPath = networkInput.iterator().next().split(SPACE_DELIMITER);
        final String[] pathAndQuery = httpMethodAndPath[URL_INDEX].split(QUERY_DELIMITER);

        this.httpMethod = HttpMethod.valueOf(httpMethodAndPath[METHOD_INDEX]);
        this.protocol = httpMethodAndPath[PROTOCOL_INDEX];
        this.path = pathAndQuery[PATH_INDEX];
        this.queryParameter = new QueryParameter((pathAndQuery.length == NO_QUERY) ? EMPTY : pathAndQuery[QUERY_INDEX]);
        this.requestFields = makeFields(networkInput);

        final int contentLength = Integer.parseInt(requestFields.getOrDefault(CONTENT_LENGTH, ZERO_LENGTH));
        this.body = (contentLength > ZERO) ? networkInput.readBody(contentLength) : EMPTY;
        this.queryParameter.putByRawQueries(this.body);

        LOG.debug("Request - protocol: {}, method: {}, path: {}, parameter: {}\nbody: {}",
                this.protocol,
                this.httpMethod,
                this.path,
                this.queryParameter.getQueries().toString(),
                this.body);
    }

    private Map<String, String> makeFields(final NetworkInput networkInput) {
        final Map<String, String> fields = new HashMap<>();
        for (String input : networkInput) {
            fields.put(makeKey(input), makeValue(input));
        }
        return Collections.unmodifiableMap(fields);
    }

    private String makeKey(final String rawField) {
        return rawField.split(KEY_VALUE_DELIMETER)[KEY_INDEX].trim().toLowerCase();
    }

    private String makeValue(final String rawField) {
        return rawField.split(KEY_VALUE_DELIMETER)[VALUE_INDEX].trim();
    }

    public String getHttpMethod() {
        return httpMethod.name();
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public QueryParameter getQueryParameter() {
        return queryParameter;
    }

    public Map<String, String> getRequestFields() {
        return requestFields;
    }

    public String getFieldsValue(final String key) {
        return requestFields.getOrDefault(key, EMPTY);
    }
}
