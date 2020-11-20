package application.filter.auth;

import session.Session;

public class Authority {

    public static void validateAuthority(Session session) throws UnauthorizedException {
        try {
            if (session.getAttribute("login").equals(true)) {
                return;
            }
            throw new UnauthorizedException();
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException();
        }
    }
}
