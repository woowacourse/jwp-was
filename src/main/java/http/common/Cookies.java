package http.common;

import java.util.ArrayList;
import java.util.List;

public class Cookies {

    private static final String NEW_LINE = "\r\n";
    private List<Cookie> cookies = new ArrayList<>();

    public Cookies() {
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public boolean isExist() {
        return cookies.size() != 0;
    }

    public int getSize() {
        return cookies.size();
    }

    public Cookie getCookie(int index) {
        return cookies.get(index);
    }

    public String getAllCookiesString() {
        StringBuilder stringBuilder = new StringBuilder();
        cookies.forEach(cookie -> stringBuilder.append(cookies).append(NEW_LINE));
        return stringBuilder.toString();
    }
}
