package web;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RequestBody {
    public static final String PARAMETER_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";
    public static final int INDEX_ZERO = 0;
    public static final int INDEX_ONE = 1;

    private final String body;

    public RequestBody(String body) {
        this.body = body;
    }

    public Map<String, String> parseParameters() {
        final String[] pairs = this.body.split(PARAMETER_DELIMITER);

        return Arrays.stream(pairs)
                .map(parameter -> parameter.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(it -> it[INDEX_ZERO], it -> it[INDEX_ONE]));
    }

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestBody that = (RequestBody) o;
        return Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }
}
