package net.slipp.presentation.dto.parser;

import net.slipp.presentation.dto.JoinRequest;
import net.slipp.presentation.dto.LoginRequest;

import kr.wootecat.dongle.model.http.request.HttpRequest;

public class RequestDTOParser {

    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_EMAIL = "email";

    private RequestDTOParser() {
    }

    public static JoinRequest toJoinRequest(HttpRequest request) {
        String userId = request.getParameter(PARAM_USER_ID);
        String password = request.getParameter(PARAM_PASSWORD);
        String name = request.getParameter(PARAM_NAME);
        String email = request.getParameter(PARAM_EMAIL);

        return new JoinRequest(userId, password, name, email);
    }

    public static LoginRequest toLoginRequest(HttpRequest request) {
        String userId = request.getParameter(PARAM_USER_ID);
        String password = request.getParameter(PARAM_PASSWORD);

        return new LoginRequest(userId, password);
    }
}
