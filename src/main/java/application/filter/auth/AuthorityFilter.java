package application.filter.auth;

import request.HttpRequest;

public class AuthorityFilter {

    public static void validateAuthority(HttpRequest httpRequest) throws UnauthorizedException {
        try {
            String cookie = httpRequest.getHeader("Cookie");

            if (!cookie.contains("login=true")) {    // Todo: 로직 수정
                throw new UnauthorizedException();
            }
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException();
        }
    }
}
