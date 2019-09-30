package webserver.message.request;

import webserver.message.HttpCookie;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class RequestCookieParser {
    private static final String ATTRIBUTE_DELIMITER = ";";
    private static final String COOKIE_KEY_VALUE_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    public static List<HttpCookie> parse(final String rawCookies) {
        return seperateCookies(rawCookies).entrySet().stream()
                .map(cookie -> new HttpCookie.Builder(cookie.getKey(), cookie.getValue()).build())
                .collect(Collectors.toList());
    }

    private static Map<String, String> seperateCookies(String cookie) {
        return Arrays.stream(Optional.ofNullable(cookie).orElse("").split(ATTRIBUTE_DELIMITER))
                .map(query -> query.split(COOKIE_KEY_VALUE_DELIMITER, 2))
                .filter(RequestCookieParser::queryFilter)
                .collect(Collectors.toUnmodifiableMap(array -> array[KEY_INDEX].trim(), array -> array[VALUE_INDEX].trim()));
    }

    private static boolean queryFilter(final String[] keyValue) {
        return keyValue.length == 2 && !keyValue[KEY_INDEX].isEmpty() && !keyValue[VALUE_INDEX].isEmpty();
    }

}
