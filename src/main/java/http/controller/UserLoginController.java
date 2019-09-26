package http.controller;

import db.DataBase;
import http.common.Cookie;
import http.common.HttpSession;
import http.exception.NotFoundUserException;
import http.exception.NotMatchPasswordException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import webserver.SessionHandler;

import java.util.Optional;

import static http.common.Cookie.LOGINED;
import static http.common.Cookie.LOGINED_TRUE;

public class UserLoginController extends AbstractController {
    public static final String URL = "/user/login";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        //Todo 에러 페이지
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        String requestUserId = request.getData("userId");
        String requestPassword = request.getData("password");
        checkLogin(response, requestUserId, requestPassword);
        setLoginSuccessResponse(response);
    }

    private void checkLogin(HttpResponse response, String requestUserId, String requestPassword) {
        Optional<User> mayBeUser = Optional.ofNullable(DataBase.findUserById(requestUserId));
        User user = mayBeUser.orElseThrow(() -> {
            setLoginFailResponse(response);
            return new NotFoundUserException("존재하지 않는 유저입니다.");
        });
        if (!user.isEqualPassword(requestPassword)) {
            setLoginFailResponse(response);
            throw new NotMatchPasswordException("비밀번호가 일치하지 않습니다.");
        }
    }

    private void setLoginSuccessResponse(HttpResponse response) {
        HttpSession session = new HttpSession();
        Cookie loginedCookie = new Cookie(LOGINED, LOGINED_TRUE);
        loginedCookie.addOption(Cookie.PATH, "/");
        session.setAttribute(loginedCookie.getName(), loginedCookie);
        SessionHandler.getInstance().addSession(session.getId(), session);

        response.addCookie("sessionId", session.getId());
        response.sendRedirect("/index.html");
    }

    private void setLoginFailResponse(HttpResponse response) {
        response.sendRedirect("/user/login_failed.html");
    }


}
