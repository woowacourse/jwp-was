package http.request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

public enum ContentType {
    APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded", ContentType::decode),
    APPLICATION_JSON("application/json", Function.identity());

    private String type;
    private Function<String, String> parse;

    ContentType(String type, Function<String, String> parse) {
        this.type = type;
        this.parse = parse;
    }

    public String parse(String input) {
        return parse.apply(input);
    }

    private static String decode(String input) {
        try {
            return URLDecoder.decode(input, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }
    }
}
