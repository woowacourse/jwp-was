package servlet.resolver;

import domain.User;
import http.request.HttpRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;

public class UserResolver {
    public static User resolve(HttpRequest httpRequest) throws UnsupportedEncodingException {
        return new User(
                decode(httpRequest.getParameter("userId")),
                httpRequest.getParameter("password"),
                decode(httpRequest.getParameter("name")),
                decode(httpRequest.getParameter("email"))
        );
    }

    private static String decode(String element) throws UnsupportedEncodingException {
        String optElement = Optional.ofNullable(element).orElse("");
        return URLDecoder.decode(optElement, "UTF-8");
    }
}
