package webserver.http.header;

import webserver.http.body.DefaultHttpBody;
import webserver.http.body.HttpBody;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

public enum HttpContentType {
    APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded", DefaultHttpBody::from);

    private final String name;
    private final Function<String, HttpBody> httpBodyCreator;

    HttpContentType(String name, Function<String, HttpBody> httpBodyCreator) {
        this.name = name;
        this.httpBodyCreator = httpBodyCreator;
    }

    public static HttpContentType from(String name) {
        return Arrays.stream(HttpContentType.values())
                .filter(type -> type.name.equalsIgnoreCase(name))
                .findFirst()
                .orElse(APPLICATION_X_WWW_FORM_URLENCODED);
    }

    public HttpBody createHttpBody(String body) {
        Objects.requireNonNull(body);

        return this.httpBodyCreator.apply(body);
    }
}
