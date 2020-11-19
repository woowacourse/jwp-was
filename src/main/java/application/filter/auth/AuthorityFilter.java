package application.filter.auth;

import request.HttpRequest;
import request.RequestCookies;

public class AuthorityFilter {

    public static void validateAuthority(HttpRequest httpRequest) throws UnauthorizedException {
        try {
            RequestCookies cookies = RequestCookies.from(httpRequest.getHeader("Cookie"));

            if (!cookies.isValue("login", "true")) {
                throw new UnauthorizedException();
            }
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException();
        }
    }
}
