package http.cookie;

import utils.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class CookieParser {
    private static final String NAME_VALUE_SEPARATOR = "=";
    private static final int INVALID_SEPARATOR_POSITION = 0;

    public static Cookies parse(List<String> cookies) {
        if (cookies == null) {
            return new Cookies();
        }

        return cookies.stream()
                .map(CookieParser::extractCookie)
                .filter(Cookie::isNotEmpty)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Cookies::new));
    }

    private static Cookie extractCookie(String cookie) {
        int separatorPosition = getSeparatorPosition(cookie);

        if (separatorPosition > INVALID_SEPARATOR_POSITION) {
            String name = cookie.substring(0, separatorPosition);
            String value = cookie.substring(separatorPosition + 1);
            return new Cookie.Builder(name, value).build();
        }

        return Cookie.EMPTY;
    }

    private static int getSeparatorPosition(String cookie) {
        return StringUtils.isEmpty(cookie) ? INVALID_SEPARATOR_POSITION : cookie.indexOf(NAME_VALUE_SEPARATOR);
    }
}
