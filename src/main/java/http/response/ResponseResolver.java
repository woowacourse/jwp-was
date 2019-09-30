package http.response;

import http.response.view.View;
import session.CookieSessionResolver;
import session.SessionResolver;

public class ResponseResolver {

    public static void resolve(View view, HttpResponse response) {
        response.addStatusCode(view.getResponseStatus());
        response.addHeader(view.getHeader());
        response.addBody(view.getBody());

        SessionResolver sessionResolver = new CookieSessionResolver();
        sessionResolver.resolve(response);
    }
}
