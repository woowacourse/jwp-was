package http.common;

import java.util.List;

public class Cookies {

    private List<Cookie> cookies;

    public Cookies(final List<Cookie> cookies) {
        this.cookies = cookies;
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        cookies.forEach(cookie -> stringBuilder.append(cookie).append("\r\n"));
        return stringBuilder.toString();
    }
}
