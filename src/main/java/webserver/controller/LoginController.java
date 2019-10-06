package webserver.controller;

import db.DataBase;
import http.Cookie;
import http.Session;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class LoginController extends AbstractController {

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        if (request.hasParameters()) {
            // 만약에 이 데이터를 가져오는 부분이 실패하면 400 관련 애러이지 않을까?
            // (여기서 해결할 방법이 없기에)
            // 그렇다면 애러 페이지까지 예외를 던지는게 맞지 않으려나??
            String userId = request.getParameter("userId");
            String password = request.getParameter("password");

            User user = DataBase.findUserById(userId);

            String redirectLocation = "/index.html";
            if (user == null || !user.getPassword().equals(password)) {
                // 302 관련해서 묶어줄 수 있을 것 같다
                redirectLocation = "/user/login_failed.html";
                response.setHeader("Location", redirectLocation);
                response.response302Header();
                return;
            }

            Session session = request.getSession(true).get();
            session.setAttribute("user", user);

            // response.setCookie() 같은 식으로 해결해줄 수 있지 않을까?
            Cookie cookie = Cookie.create();
            cookie.add("JWP_WAS_SESSION_ID", session.getId());

            response.setHeader("Set-Cookie", cookie.toHeaderValue());
            response.setHeader("Location", redirectLocation);
            response.response302Header();
        }
    }
}
