package cookie;

import java.util.ArrayList;
import java.util.List;

import domain.HttpHeader;
import domain.HttpResponse;

public class HttpCookie {
    private final List<String> cookies;

    public HttpCookie() {
        this.cookies = new ArrayList<>();
    }

    public void add(String cookie) {
        cookies.add(cookie);
    }

    public void apply(HttpResponse httpResponse) {
        String result = String.join("; ", cookies);
        httpResponse.addHeader(HttpHeader.SET_COOKIE, result);
    }
}
