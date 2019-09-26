package controller.user;

import controller.AbstractController;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.HttpSession;
import session.HttpSessionRepository;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.ResponseMetaData;
import webserver.response.ResponseMetaDataGenerator;

import java.io.IOException;

public class UserLoginController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(UserLoginController.class);

    @Override
    public void service(final HttpRequest request, final HttpResponse response) throws IOException {
        String userId = request.findRequestBodyParam("userId");
        String password = request.findRequestBodyParam("password");

        log.debug("login request : userId={}", userId);

        ResponseMetaData responseMetaData = ResponseMetaDataGenerator.buildFailedLoginResponseMetaData(request, "/user/login_failed.html");

        User user = DataBase.findUserById(userId);
        if (isValidLogin(user, password)) {
            log.debug("login success : userId={}", userId);
            HttpSession httpSession = createHttpSession();
            responseMetaData = ResponseMetaDataGenerator.buildSuccessfulLoginResponseMetaData(request, "/index.html", httpSession.getId());
        }

        response.setResponseMetaData(responseMetaData);
        doPost(request, response);
    }

    private boolean isValidLogin(final User user, final String password) {
        return user != null && user.matchPassword(password);
    }

    private HttpSession createHttpSession() {
        HttpSession httpSession = new HttpSession();
        String sessionId = httpSession.getId();
        HttpSessionRepository.setSession(sessionId, httpSession);

        return httpSession;
    }
}
