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

    private final Map<String, String> formData;

    public RequestBody(String body) {
        this.formData = parseBodyData(body);
    }

    private Map<String, String> parseBodyData(String body) {
        String[] data = body.split(PARAMETER_DELIMITER);

        return Arrays.stream(data)
                .map(parameter -> parameter.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(it -> it[INDEX_ZERO], it -> it[INDEX_ONE]));
    }

    public Map<String, String> getFormData() {
        return formData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestBody that = (RequestBody) o;
        return Objects.equals(formData, that.formData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formData);
    }
}
