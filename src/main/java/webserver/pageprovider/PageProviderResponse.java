package webserver.pageprovider;

import http.Cookie;
import http.response.HttpResponse;
import http.response.HttpResponseAccessor;

public class PageProviderResponse implements HttpResponseAccessor {
    private final HttpResponse response;

    private PageProviderResponse(HttpResponse response) {
        this.response = response;
    }

    public static HttpResponseAccessor from(HttpResponse response) {
        return new PageProviderResponse(response);
    }

    @Override
    public void setCookie(Cookie cookie) {
        response.setCookie(cookie);
    }
}
