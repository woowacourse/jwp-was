package webserver.http.request;

import java.util.Arrays;
import java.util.function.Predicate;

public enum RequestURIType {
    NO_PARAMS(uri -> !uri.contains("?")),
    HAVE_PARAMS(uri -> uri.contains("?"));

    private final Predicate<String> judge;

    RequestURIType(Predicate<String> judge) {
        this.judge = judge;
    }

    public static RequestURIType of(String uri) {
        return Arrays.stream(values())
                .filter(value -> value.judge.test(uri))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("지원하지 않는 uri 입니다. uri : " + uri));
    }

    public boolean hasParams() {
        return this == HAVE_PARAMS;
    }
}
