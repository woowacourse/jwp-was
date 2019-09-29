package session;

import http.HttpCookie;
import http.response.HttpResponse;

public class CookieSessionResolver implements SessionResolver {
    private static final String EVERYWHERE = "/";

    @Override
    public void resolve(HttpResponse response) {
        HttpSession httpSession = response.getSession();
        if (httpSession != null) {
            HttpCookie httpCookie = new HttpCookie(HttpSession.SESSION_ID, httpSession.getId());
            httpCookie.setPath(EVERYWHERE);
            response.addCookie(httpCookie);
        }
    }
}
