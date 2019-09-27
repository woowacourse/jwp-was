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
    private static final String COOKIE_KEY_VALUE_DELIMITER = "=";
    private static final String HEADER_DELIMITER = ": ";
    private static final String PARAM_DELIMITER = "&";
    private static final String COOKIE_DELIMITER = "; ";
    private static final String PARAM_KEY_VALUE_DELIMITER = "=";
    public static final String UTF_8 = "UTF-8";

    public static Map<String, String> parse(List<String> lines) {
        return buildHeaders(lines, new HashMap<>());
    }

    private static Map<String, String> buildHeaders(List<String> lines, Map<String, String> headers) {
        for (String line : lines) {
            String[] splitLine = line.split(HEADER_DELIMITER);
            headers.put(splitLine[0], splitLine[1]);
        }
        return headers;
    }

    public static Map<String, String> parse(HttpRequestUrl url) {
        Map<String, String> params = new HashMap<>();
        if (!url.hasParams() || url.isEmptyParams()) {
            return params;
        }
        return buildParams(HttpRequestUtils.decode(url.getParams()), params);
    }

    private static Map<String, String> buildParams(String dataSequence, Map<String, String> params) {
        String[] tokens = dataSequence.split(PARAM_DELIMITER);
        for (String token : tokens) {
            String[] paramKeyValue = token.split(PARAM_KEY_VALUE_DELIMITER);
            params.put(paramKeyValue[0], paramKeyValue[1]);
        }
        return params;
    }

    public static Map<String, String> parse(String bodyData) {
        Map<String, String> body = new HashMap<>();
        if ("".equals(bodyData)) {
            return body;
        }
        return buildParams(HttpRequestUtils.decode(bodyData), body);
    }

    public static String filePathBuilder(String path, MimeType mimeType) {
        if (!mimeType.isHtml()) {
            return STATIC_BASE_PATH + path;
        }
        return TEMPLATES_BASE_PATH + path;
    }

    public static Map<String, String> parseCookie(String sequence) {
        String[] tokens = sequence.split(COOKIE_DELIMITER);
        Map<String, String> cookies = new HashMap<>();
        for (String token : tokens) {
            String[] splitToken = token.split(COOKIE_KEY_VALUE_DELIMITER);
            cookies.put(splitToken[0], splitToken[1]);
        }
        return cookies;
    }

    private static String decode(String unicode) {
        try {
            return URLDecoder.decode(unicode, UTF_8);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
