package http.request.factory;

import http.HttpCookie;
import http.request.HttpCookieStore;
import http.request.HttpRequestHeader;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class HttpRequestHeaderFactory {
    private static final int KEY = 0;
    private static final int VALUE = 1;
    private static final String COOKIE = "Cookie";

    public static HttpRequestHeader create(List<String> lines) {
        Map<String, String> fields = new HashMap<>();
        lines.forEach(line -> {
            String[] element = line.split(": ");
            fields.put(element[KEY], element[VALUE]);
        });

        if (fields.containsKey(COOKIE)) {
            HttpCookieStore httpCookieStore = createHttpCookieStore(fields.get(COOKIE));
            fields.remove(COOKIE);
            return new HttpRequestHeader(fields, httpCookieStore);
        }

        return new HttpRequestHeader(fields, new HttpCookieStore(Collections.EMPTY_LIST));
    }

    private static HttpCookieStore createHttpCookieStore(String HeaderCookies) {
        return new HttpCookieStore(
                Arrays.stream(HeaderCookies.split(";"))
                        .map(token -> {
                            String[] cookieKeyValue = token.split("=");
                            return new HttpCookie(cookieKeyValue[0].trim(), cookieKeyValue[1]);
                        })
                        .collect(toList())
        );
    }
}
