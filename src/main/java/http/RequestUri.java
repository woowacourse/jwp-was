package http;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestUri {
    public static final String DELIMITER_OF_URI_AND_DATA = "\\?";
    public static final String DELIMITER_OF_PARAM_DATA = "&";
    public static final String DELIMITER_OF_KEY_VALUE = "=";

    private HttpMethod method;
    private String uri;

    public RequestUri(String line) {
        this.method = HttpMethod.valueOf(line.split(" ")[0]);
        this.uri = line.split(" ")[1];
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public Map<String, String> makeRequestParam() {
        Map<String, String> params = new HashMap<>();
        String paramString = uri.split(DELIMITER_OF_URI_AND_DATA)[1];
        String[] paramData = paramString.split(DELIMITER_OF_PARAM_DATA);
        for (String paramDatum : paramData) {
            params.put(paramDatum.split(DELIMITER_OF_KEY_VALUE)[0], paramDatum.split(DELIMITER_OF_KEY_VALUE)[1]);
        }
        return params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RequestUri that = (RequestUri) o;
        return getMethod() == that.getMethod() &&
                Objects.equals(getUri(), that.getUri());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMethod(), getUri());
    }
}
