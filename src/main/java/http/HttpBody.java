package http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import exception.DisabledEncodingException;

public class HttpBody {
    private static final HttpBody DEFAULT_REQUEST_BODY =
        new HttpBody(Collections.unmodifiableMap(new HashMap<>()));
    private static final String HTTP_BODY_SPLITTER = "&";
    private static final String HTTP_KEY_VALUE_SPLITTER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private Map<String, String> body;

    public static HttpBody from(String input) {
        System.out.println(input);
        if (Objects.isNull(input) || input.isEmpty()) {
            return DEFAULT_REQUEST_BODY;
        }
        Map<String, String> body = Arrays.stream(input.split(HTTP_BODY_SPLITTER))
            .map(splited -> splited.split(HTTP_KEY_VALUE_SPLITTER))
            .collect(Collectors.toMap(
                pair -> pair[KEY_INDEX], pair -> {
                    try {
                        return URLDecoder.decode(pair[VALUE_INDEX], "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        throw new DisabledEncodingException("HttpBody 생성에 실패하였습니다 - 인코딩 실패", e);
                    }
                }
            ));
        return new HttpBody(body);
    }

    private HttpBody(Map<String, String> body) {
        this.body = body;
    }

    @Nullable
    public String get(String name) {
        return body.get(name);
    }
}
