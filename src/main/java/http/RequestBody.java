package http;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RequestBody {
    public static final String PARAMETER_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";

    private final Map<String, String> formData;

    public RequestBody(BufferedReader br, int contentLength) throws IOException {
        String body = IOUtils.readData(br, contentLength);
        this.formData = parseBodyData(body);
    }

    private Map<String, String> parseBodyData(String body) {
        if (body.isEmpty()) {
            return null;
        }

        String[] data = body.split(PARAMETER_DELIMITER);

        return Arrays.stream(data)
                .map(parameter -> parameter.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(it -> it[0], it -> it[1]));
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
