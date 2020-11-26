package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class HttpUtils {
    private static final String DEFAULT_CHARACTER_SET = "UTF-8";

    private HttpUtils() {
    }

    public static String urlDecode(String url) {
        try {
            return URLDecoder.decode(url, DEFAULT_CHARACTER_SET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException();
        }
    }
}
