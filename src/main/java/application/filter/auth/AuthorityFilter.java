package application.filter.auth;

import request.HttpRequest;

public class AuthorityFilter {

    public static void validateAuthority(HttpRequest httpRequest) throws UnauthorizedException {
        try {
            String cookie = httpRequest.getHeader("Cookie");

            if (!cookie.contains("login=true")) {
                throw new UnauthorizedException();
            }
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException();
        }
    }
}
