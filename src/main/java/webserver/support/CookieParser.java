package webserver.support;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

public class CookieParser {

    public static Map<String, String> parse(String cookieValue) {
        if (StringUtils.isBlank(cookieValue)) {
            return Collections.emptyMap();
        }

        Map<String, String> cookies = Maps.newHashMap();
        String[] splitedCookie = cookieValue.split("; ");

        for (String cookie : splitedCookie) {
            String[] token = cookie.split("=");
            String key = token[0];
            String value = token[1];

            cookies.put(key, value);
        }

        return cookies;
    }
}
