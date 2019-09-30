package dev.luffy.utils;

import java.util.HashMap;
import java.util.Map;

import static dev.luffy.utils.HttpRequestUtils.buildParams;

public class BodyDataParser {

    private static final String EMPTY_STRING = "";

    public static Map<String, String> parse(String bodyData) {
        Map<String, String> body = new HashMap<>();
        if (EMPTY_STRING.equals(bodyData)) {
            return body;
        }
        return buildParams(HttpRequestUtils.decode(bodyData), body);
    }
}
