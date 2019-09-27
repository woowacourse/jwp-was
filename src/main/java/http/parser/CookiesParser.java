package http.parser;

import http.common.Cookies;

import java.util.List;

public class CookiesParser {

    private static final String SET_COOKIE_DELIMITER = ":";
    private static final String SET_COOKIE = "Set-Cookie";

    public static Cookies parse(List<String> lines) {
        Cookies cookies = new Cookies();

        lines.forEach(line -> {
            String[] lineTokens = line.split(SET_COOKIE_DELIMITER, 2);
            String name = lineTokens[0].trim();
            if (SET_COOKIE.equals(name)) {
                cookies.addCookie(CookieParser.parse(line));
            }
        });
        return cookies;
    }
}
