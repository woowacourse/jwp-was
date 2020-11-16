package user.service.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.was.controller.annotation.Controller;
import http.was.controller.annotation.RequestMapping;
import http.was.exception.HttpRequestMethodNotSupportedException;
import http.was.exception.IllegalRequestException;
import http.was.http.HttpMethod;
import http.was.http.request.Cookie;
import http.was.http.request.Request;
import http.was.http.request.RequestBody;
import http.was.http.response.Response;
import http.was.http.session.HttpSession;
import http.was.http.session.WebSession;
import user.service.db.DataBase;
import user.service.model.User;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(path = "/user/login", method = HttpMethod.POST)
    public void doPost(Request request, Response response) {
        try {
            RequestBody requestBody = request.getRequestBody();
            Map<String, String> requestBodies = requestBody.parseRequestBody();
            User user = DataBase.findUserById(requestBodies.get("userId"));
            validate(requestBodies, user);

            HttpSession httpSession = request.getHttpSession(true);
            httpSession.setAttribute("email", user.getEmail());

            String id = httpSession.getId();
            Cookie cookie = new Cookie(WebSession.DEFAULT_SESSION_COOKIE_NAME, id + "; path=/");

            response.addCookie(cookie);
            response.found("/index.html");
        } catch (IllegalRequestException e) {
            logger.info(e.getMessage());
            response.found("/user/login_failed.html");
        }
    }

    @RequestMapping(path = "/user/login", method = HttpMethod.GET)
    public void doGet(Request request, Response response) {
        throw new HttpRequestMethodNotSupportedException(HttpMethod.GET);
    }

    private void validate(Map<String, String> requestBodies, User user) throws IllegalRequestException {
        if (user == null || !user.checkPassword(requestBodies.get("password"))) {
            throw new IllegalRequestException("로그인에 실패했습니다.");
        }
    }
}
