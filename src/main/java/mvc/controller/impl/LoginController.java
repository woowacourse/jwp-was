package mvc.controller.impl;

import mvc.controller.AbstractController;
import mvc.db.DataBase;
import mvc.exception.LoginException;
import mvc.model.User;
import org.slf4j.Logger;
import webserver.message.request.Request;
import webserver.message.request.RequestBody;
import webserver.message.response.Response;

import static org.slf4j.LoggerFactory.getLogger;


public class LoginController extends AbstractController {
    private static final Logger LOG = getLogger(LoginController.class);

    private static final String NOT_FOUND_USER_ID_MESSAGE = "해당 사용자가 존재하지 않습니다.";
    private static final String UNMATCHED_USER_MESSAGE = "비밀번호가 일치하지 않습니다.";
    private static final String INDEX_PAGE_URL = "/";

    @Override
    protected Response doPost(final Request request) {
        final RequestBody body = request.getBody();
        final String userId = body.getQueryValue("userId");
        final String password = body.getQueryValue("password");
        final User user = DataBase.findUserById(userId).orElseThrow(() -> new LoginException(NOT_FOUND_USER_ID_MESSAGE));

        if(!matchesUser(user, password)) {
            throw new LoginException(UNMATCHED_USER_MESSAGE);
        }

        return new Response.Builder().redirectUrl(INDEX_PAGE_URL).build();
    }

    private boolean matchesUser(User user, String password) {
        return user.matchesPassword(password);
    }
}
