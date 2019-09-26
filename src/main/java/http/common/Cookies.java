package http.common;

import java.util.ArrayList;
import java.util.List;

public class Cookies {

    private List<Cookie> cookies;

    private Cookies() {
        this.cookies = new ArrayList<>();
    }


    public static Cookies create(){
        return new Cookies();
    }

    public Cookies addCookie(Cookie cookie) {
        cookies.add(cookie);
        return this;
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
