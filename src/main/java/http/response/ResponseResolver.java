package http.response;

import http.HttpCookie;
import http.response.view.View;
import session.HttpSession;

public class ResponseResolver {
    private static final String EVERYWHERE = "/";

    public static void resolve(View view, HttpResponse response) {
        response.addStatusCode(view.getResponseStatus());
        response.addHeader(view.getHeader());
        response.addBody(view.getBody());
        resloveSession(response);
    }

    private static void resloveSession(HttpResponse response) {
        HttpSession httpSession = response.getSession();
        if (httpSession != null) {
            HttpCookie httpCookie = new HttpCookie(HttpSession.SESSION_ID, httpSession.getId());
            httpCookie.setPath(EVERYWHERE);
            response.addCookie(httpCookie);
        }
    }
}
