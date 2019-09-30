package dev.luffy.utils;

import dev.luffy.http.request.HttpRequestUrl;

import java.util.HashMap;
import java.util.Map;

public class UrlParameterParser {

    public static Map<String, String> parse(HttpRequestUrl url) {
        Map<String, String> params = new HashMap<>();
        if (!url.hasParams() || url.isEmptyParams()) {
            return params;
        }
        return HttpRequestUtils.buildParams(HttpRequestUtils.decode(url.getParams()), params);
    }
}
