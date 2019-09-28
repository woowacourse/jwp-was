package webserver.support;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import com.google.common.collect.Lists;
import http.response.Cookie;

import java.util.Collections;
import java.util.List;

public class CookieParser {

    public static List<Cookie> parse(String cookieValue) {
        if (StringUtils.isBlank(cookieValue)) {
            return Collections.emptyList();
        }

        List<Cookie> cookies = Lists.newArrayList();
        String[] splitedCookie = cookieValue.split("; ");

        for (String cookie : splitedCookie) {
            String[] token = cookie.split("=");
            String name = token[0];
            String value = token[1];

            cookies.add(Cookie.builder().name(name).value(value).build());
        }

        return cookies;
    }
}
