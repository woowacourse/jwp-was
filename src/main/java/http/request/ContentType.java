package http.request;

import java.util.function.Function;

import utils.IOUtils;

public enum ContentType {
    APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded", IOUtils::decode),
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
}
