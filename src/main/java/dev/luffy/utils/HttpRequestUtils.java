package dev.luffy.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.luffy.http.MimeType;
import dev.luffy.http.request.HttpRequestUrl;

public class HttpRequestUtils {

    private static final String STATIC_BASE_PATH = "./static";
    private static final String TEMPLATES_BASE_PATH = "./templates";
    private static final String PARAM_DELIMITER = "&";
    private static final String PARAM_KEY_VALUE_DELIMITER = "=";
    private static final String UTF_8 = "UTF-8";

    public static String filePathBuilder(String path, MimeType mimeType) {
        if (!mimeType.isHtml()) {
            return STATIC_BASE_PATH + path;
        }
        return TEMPLATES_BASE_PATH + path;
    }

    static Map<String, String> buildParams(String dataSequence, Map<String, String> params) {
        String[] tokens = dataSequence.split(PARAM_DELIMITER);
        for (String token : tokens) {
            String[] paramKeyValue = token.split(PARAM_KEY_VALUE_DELIMITER);
            params.put(paramKeyValue[0], paramKeyValue[1]);
        }
        return params;
    }

    static String decode(String unicode) {
        try {
            return URLDecoder.decode(unicode, UTF_8);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
