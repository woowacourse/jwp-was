package webserver.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.BiFunction;

public class Cookies {
    private static final String EMPTY = "";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String PATH = "Path";
    private static final String DOMAIN = "Domain";
    private static final String SECURE = "Secure";
    private static final String EXPIRES = "Expires";
    private static final String SAME_SITE = "SameSite";
    private static final String HTTP_ONLY = "HttpOnly";
    private static final String MAX_AGE = "Max-Age";
    private static final Map<String, BiFunction<String, Cookie, Cookie>> OPTION_MAPPER = new HashMap<>();
    private static final DateTimeFormatter HTML_TIME_FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneOffset.UTC);
    private static final Cookie EMPTY_COOKIE = new Cookie();

    static {
        OPTION_MAPPER.put(EXPIRES.toLowerCase(), (value, cookie) -> cookie.setExpires(parseExpires(value)));
        OPTION_MAPPER.put(MAX_AGE.toLowerCase(), (value, cookie) -> cookie.setMaxAge(Integer.parseInt(value)));
        OPTION_MAPPER.put(DOMAIN.toLowerCase(), (value, cookie) -> cookie.setDomain(value));
        OPTION_MAPPER.put(PATH.toLowerCase(), (value, cookie) -> cookie.setPath(value));
        OPTION_MAPPER.put(SECURE.toLowerCase(), (value, cookie) -> cookie.setSecure(true));
        OPTION_MAPPER.put(HTTP_ONLY.toLowerCase(), (value, cookie) -> cookie.setHttpOnly(true));
        OPTION_MAPPER.put(SAME_SITE.toLowerCase(), (value, cookie) -> cookie.setSameSite(value));
    }

    private final Map<String, Cookie> cookies;

    private Cookies() {
        cookies = new HashMap<>();
    }

    public Cookies(final String rawCookies) {
        this();
        final List<Cookie> cookieList = new Maker(rawCookies).makeList();
        for (final Cookie cookie : cookieList) {
            cookies.put(cookie.getName(), cookie);
        }
    }

    public Cookie getCookie(final String name) {
        return cookies.getOrDefault(name, new Cookie());
    }

    public Cookie addCookie(final Cookie cookie) {
        return cookies.put(cookie.getName(), cookie);
    }

    public Collection<Cookie> getCollection() {
        return cookies.values();
    }

    public String getCookieValue(final String name) {
        return cookies.getOrDefault(name, EMPTY_COOKIE).getValue();
    }

    public Cookie setCookieValue(final String name, final String value) {
        final Cookie cookie = getCookie(name);
        cookie.setValue(value);
        return cookie;
    }

    public Cookies merge(final Cookies cookies) {
        for (final Cookie cookie : cookies.getCollection()) {
            this.cookies.put(cookie.getName(), cookie);
        }
        return this;
    }

    private static ZonedDateTime parseExpires(final String text) throws DateTimeParseException {
        return ZonedDateTime.parse(text, HTML_TIME_FORMATTER);
    }

    private static class Maker {
        private static final int KEY_INDEX = 0;
        private static final int VALUE_INDEX = 1;
        private static final int LIMIT = 2;
        private static final String UTF_8 = "UTF-8";
        private static final String COOKIE_DELIMITER = ";";
        private final Map<String, String> cookieJar = new HashMap<>();
        private final Cookie optionCookie = new Cookie();

        Maker(final String rawCookies) {
            if (Objects.isNull(rawCookies)) {
                return;
            }
            final String[] fragments = rawCookies.split(COOKIE_DELIMITER);
            for (final String fragment : fragments) {
                processFragment(fragment.trim());
            }
        }

        private void processFragment(final String fragment) {
            final String[] maybeTuple = fragment.split(KEY_VALUE_DELIMITER, LIMIT);
            if (maybeTuple.length == LIMIT) {
                processTuple(maybeTuple);
                return;
            }
            processBooleanOption(maybeTuple[KEY_INDEX]);
        }

        private void processTuple(final String[] tuple) {
            final String key = tuple[KEY_INDEX];
            final String value = tuple[VALUE_INDEX];
            final String keyLowerCase = key.toLowerCase();
            if (OPTION_MAPPER.containsKey(keyLowerCase)) {
                OPTION_MAPPER.get(keyLowerCase).apply(value, optionCookie);
                return;
            }
            cookieJar.put(urlDecode(key), urlDecode(value));
        }

        private void processBooleanOption(final String option) {
            OPTION_MAPPER.getOrDefault(option.toLowerCase(), (value, cookie) -> cookie).apply(null, optionCookie);
        }

        private String urlDecode(final String encodedString) {
            try {
                return URLDecoder.decode(encodedString, UTF_8);
            } catch (final UnsupportedEncodingException ignored) {
                return EMPTY;
            }
        }

        List<Cookie> makeList() {
            final List<Cookie> cookies = new ArrayList<>();
            try {
                for (final String key : cookieJar.keySet()) {
                    final Cookie item = optionCookie.clone();
                    item.setName(key);
                    item.setValue(cookieJar.get(key));
                    cookies.add(item);
                }
            } catch (final CloneNotSupportedException ignored) {}
            return cookies;
        }
    }
}
