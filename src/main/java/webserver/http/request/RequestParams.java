package webserver.http.request;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RequestParams {
    private final Map<String, List<String>> parameters;

    public RequestParams(Map<String, List<String>> parameters) {
        this.parameters = parameters;
    }

    public static RequestParams from(String params) {
        if (Objects.isNull(params) || params.isEmpty()) {
            return new RequestParams(new HashMap<>());
        }

        return Arrays.stream(params.split("&"))
            .map(param -> param.split("="))
            .collect(
                collectingAndThen(groupingBy(param -> param[0], mapping(param -> param[1], toList())),
                    RequestParams::new));
    }

    public String getOneParameterValue(String key) {
        return parameters.get(key).get(0);
    }

    public List<String> get(String key) {
        return parameters.get(key);
    }

    public Map<String, List<String>> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }
}
