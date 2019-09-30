package http.parser;

import http.common.Cookies;

import java.util.List;

public class CookiesParser {

    private static final String COOKIE_DELIMITER = ":";
    private static final String COOKIE = "Cookie";

    public static Cookies parse(List<String> lines) {
        Cookies cookies = new Cookies();

        lines.forEach(line -> {
            String[] lineTokens = line.split(COOKIE_DELIMITER, 2);
            String name = lineTokens[0].trim();
            if (COOKIE.equals(name)) {
                cookies.addCookie(CookieParser.parse(line));
            }
        });
        return cookies;
    }
}
