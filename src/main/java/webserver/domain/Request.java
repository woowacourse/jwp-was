package webserver.domain;

import org.slf4j.Logger;
import webserver.view.NetworkInput;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class Request {
    private static final Logger LOG = getLogger(Request.class);

    // TODO: 잘못된 요청을 받았을 때 문제가 없는지 검토
    // TODO: 래퍼 클래스로 감싸기
    //private final HttpMethod httpMethod;
    //private final Path path;
    private final String httpMethod;
    private final String path;
    private final String protocol;
    private final QueryParameter queryParameter;
    private final Map<String, String> requestFields;

    public Request(final NetworkInput networkInput) {
        final String[] httpMethodAndPath = networkInput.iterator().next().split(" ");
        final String[] pathAndQuery = httpMethodAndPath[1].split("\\?");

        this.httpMethod = httpMethodAndPath[0];
        this.protocol = httpMethodAndPath[2];
        this.path = pathAndQuery[0];
        this.queryParameter = new QueryParameter((pathAndQuery.length > 1) ? pathAndQuery[1] : "");
        this.requestFields = makeFields(networkInput);

        LOG.debug("Request: {}", Arrays.toString(httpMethodAndPath));
    }

    private Map<String, String> makeFields(final NetworkInput networkInput) {
        final Map<String, String> fields = new HashMap<>();
        for (String input : networkInput) {
            fields.put(makeKey(input), makeValue(input));
        }
        return Collections.unmodifiableMap(fields);
    }

    // TODO: 한번에 Key:Value를 반환하게끔 리팩토링
    private String makeKey(final String rawField) {
        return rawField.split(":")[0].trim();
    }

    private String makeValue(final String rawField) {
        return rawField.split(":")[1].trim();
    }

    public String getHttpMethod() {
        return httpMethod;
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
        return requestFields.getOrDefault(key, "");
    }
}
