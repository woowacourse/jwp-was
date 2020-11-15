package net.slipp.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.slipp.application.UserService;
import net.slipp.application.UserServiceFactory;
import net.slipp.presentation.dto.JoinRequest;
import net.slipp.presentation.dto.JoinResponse;
import net.slipp.presentation.dto.parser.RequestDTOParser;

import kr.wootecat.dongle.core.servlet.HttpServlet;
import kr.wootecat.dongle.http.request.HttpRequest;
import kr.wootecat.dongle.http.response.HttpResponse;

public class UserServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(UserServlet.class);

    private static final String CREATE_USER_COMPLETE_DEBUG_LOG_MESSAGE_FORMAT = "회원가입 완료! {}, {}";
    private static final String INDEX_URL_PATH = "/index.html";

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        createUser(request, response);
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        createUser(request, response);
    }

    private void createUser(HttpRequest request, HttpResponse response) {
        JoinRequest joinRequest = RequestDTOParser.toJoinRequest(request);
        UserService userService = UserServiceFactory.getInstance();
        JoinResponse joinResponse = userService.create(joinRequest);
        logger.debug(CREATE_USER_COMPLETE_DEBUG_LOG_MESSAGE_FORMAT, request.getMethod(), joinResponse.getPrincipal());
        response.sendRedirect(INDEX_URL_PATH);
    }
}
