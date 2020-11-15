package net.slipp.presentation;

import static kr.wootecat.dongle.http.HttpStatus.*;
import static kr.wootecat.dongle.http.MimeType.*;

import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.slipp.application.UserService;
import net.slipp.application.UserServiceFactory;
import net.slipp.application.exception.AuthenticationFailException;
import net.slipp.presentation.dto.LoginRequest;
import net.slipp.presentation.dto.parser.RequestDTOParser;

import kr.wootecat.dongle.core.servlet.HttpServlet;
import kr.wootecat.dongle.http.Cookie;
import kr.wootecat.dongle.http.request.HttpRequest;
import kr.wootecat.dongle.http.response.HttpResponse;
import utils.FileIoUtils;

public class LoginServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    private static final String INDEX_URL_PATH = "/index.html";
    private static final String LOGIN_FAIL_PATH = "./templates/user/login_failed.html";
    private static final String LOGINED = "logined";
    private static final String LOGIN_COOKIE_PATH = "/";

    private static final String LOGIN_COMPLETE_LOGGING_MESSAGE = "성공적으로 로그인 했습니다 완료! {}";

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        LoginRequest loginRequest = RequestDTOParser.toLoginRequest(request);
        UserService userService = UserServiceFactory.getInstance();
        try {
            userService.login(loginRequest);
        } catch (AuthenticationFailException e) {
            try {
                response.changeHttpStatus(UNAUTHORIZED);
                response.addCookie(new Cookie(LOGINED, false));
                response.addBody(FileIoUtils.loadFileFromClasspath(LOGIN_FAIL_PATH), HTML_UTF_8);
                return;
            } catch (IOException | URISyntaxException ioException) {
                ioException.printStackTrace();
            }
        }
        response.addCookie(new Cookie(LOGINED, true, LOGIN_COOKIE_PATH));
        logger.debug(LOGIN_COMPLETE_LOGGING_MESSAGE, loginRequest.getUserId());
        response.sendRedirect(INDEX_URL_PATH);
    }

}
