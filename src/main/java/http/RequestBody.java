package http;

import exception.DuplicateParamsException;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RequestBody {
    public static final String PARAMETER_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";

    private final Map<String, String> formData;

    public RequestBody(BufferedReader br, int contentLength) throws IOException {
        String body = IOUtils.readData(br, contentLength);
        this.formData = convertFormData(body);
    }

    private Map<String, String> convertFormData(String body) {
        if (body.isEmpty()) {
            return Collections.emptyMap();
        }

        String[] data = body.split(PARAMETER_DELIMITER);

        try {
            return Arrays.stream(data)
                    .map(parameter -> parameter.split(KEY_VALUE_DELIMITER))
                    .filter(it -> it.length == 2)
                    .collect(Collectors.toMap(it -> it[0], it -> it[1]));
        } catch (IllegalStateException e) {
            throw new DuplicateParamsException("Request Body에 중복된 키가 있습니다.");
        }
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
