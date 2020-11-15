package net.slipp.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.slipp.application.UserService;
import net.slipp.config.ServiceFactory;
import net.slipp.presentation.dto.LoginRequest;
import net.slipp.presentation.dto.parser.RequestDTOParser;

import kr.wootecat.dongle.application.http.Cookie;
import kr.wootecat.dongle.application.http.request.HttpRequest;
import kr.wootecat.dongle.application.http.response.HttpResponse;
import kr.wootecat.dongle.application.servlet.HttpServlet;

public class LoginServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    private static final String INDEX_URL_PATH = "/index.html";
    private static final String LOGINED = "logined";
    private static final String LOGIN_COOKIE_PATH = "/";
    private static final int ONE_DAY = 60 * 60 * 24;

    private static final String LOGIN_COMPLETE_LOGGING_MESSAGE = "성공적으로 로그인 했습니다 완료! {}";

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        LoginRequest loginRequest = RequestDTOParser.toLoginRequest(request);
        UserService userService = ServiceFactory.getUserService();
        userService.login(loginRequest);

        Cookie cookie = createLoginCookie();
        response.addCookie(cookie);
        logger.debug(LOGIN_COMPLETE_LOGGING_MESSAGE, loginRequest.getUserId());
        response.sendRedirect(INDEX_URL_PATH);
    }

    private Cookie createLoginCookie() {
        Cookie cookie = new Cookie(LOGINED, true);
        cookie.setPath(LOGIN_COOKIE_PATH);
        return cookie;
    }
}
