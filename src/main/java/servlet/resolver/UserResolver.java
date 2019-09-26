package servlet.resolver;

import http.request.HttpRequest;
import model.User;

//@TODO 나중에 스태틱 -> 객체
public class UserResolver {
    public static User resolve(HttpRequest httpRequest) {
        return new User(
                httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email")
        );
    }
}
