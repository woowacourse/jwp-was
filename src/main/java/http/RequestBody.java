package http;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

public class RequestBody {
    private static final RequestBody DEFAULT_REQUEST_BODY = new RequestBody(Collections.unmodifiableMap(new HashMap<>()));
    private static final String HTTP_BODY_SPLITTER = "&";
    private static final String HTTP_KEY_VALUE_SPLITTER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private Map<String, String> body;

    public static RequestBody from(String input) {
        System.out.println(input);
        if (Objects.isNull(input) || input.isEmpty()) {
            return DEFAULT_REQUEST_BODY;
        }
        Map<String, String> body = Arrays.stream(input.split(HTTP_BODY_SPLITTER))
            .map(splited -> splited.split(HTTP_KEY_VALUE_SPLITTER))
            .collect(Collectors.toMap(
                pair -> pair[KEY_INDEX], pair -> Objects.isNull(pair[VALUE_INDEX]) ? null : pair[VALUE_INDEX]
            ));
        return new RequestBody(body);
    }

    private RequestBody(Map<String, String> body) {
        this.body = body;
    }

    @Nullable
    public String get(String name) {
        return body.get(name);
    }
}
