package http;

import java.util.function.Function;
import java.util.stream.Stream;

import utils.IOUtils;

public enum ContentType {
    APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded", IOUtils::decode),
    APPLICATION_JSON("application/json", Function.identity()),
    HTML("text/html;charset=utf-8", Function.identity()),
    CSS("text/css", Function.identity()),
    JS(" text/javascript;charset=UTF-8", Function.identity()),
    ICO("image/x-icon", Function.identity()),
    TTF("font/ttf", Function.identity()),
    WWOF("font/woff", Function.identity()),
    WWOF2("font/woff2", Function.identity()),
    PLAIN("text/plain", Function.identity());

    private String type;
    private Function<String, String> parse;

    ContentType(String type, Function<String, String> parse) {
        this.type = type;
        this.parse = parse;
    }

    public static ContentType findOrDefault(String type) {
        return Stream.of(ContentType.values())
            .filter(contentType -> contentType.type.equals(type))
            .findFirst()
            .orElse(APPLICATION_X_WWW_FORM_URLENCODED);
    }

    public String parse(String input) {
        return parse.apply(input);
    }

    public String getType() {
        return type;
    }
}
