package application.auth;

import session.Session;

public class Authority {

    public static final String ATTRIBUTE_NAME_FOR_LOGIN = "login";

    public static void validateAuthority(Session session) throws UnauthorizedException {
        try {
            if (session.getAttribute(ATTRIBUTE_NAME_FOR_LOGIN).equals(true)) {
                return;
            }
            throw new UnauthorizedException();
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException();
        }
    }
}
