package jwp.was.webapplicationserver.service;

import static jwp.was.webapplicationserver.configure.security.WithLoginConfigure.ATTRIBUTE_KEY_USER;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jwp.was.webapplicationserver.configure.annotation.Autowired;
import jwp.was.webapplicationserver.configure.annotation.Service;
import jwp.was.webapplicationserver.configure.session.HttpSession;
import jwp.was.webapplicationserver.configure.session.HttpSessionImpl;
import jwp.was.webapplicationserver.configure.session.HttpSessions;
import jwp.was.webapplicationserver.controller.dto.LoginRequest;
import jwp.was.webapplicationserver.controller.dto.UserRequest;
import jwp.was.webapplicationserver.db.DataBase;
import jwp.was.webapplicationserver.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final HttpSessions httpSessions = HttpSessions.getInstance();

    @Autowired
    private DataBase dataBase;

    public User createUser(UserRequest userRequest) {
        User user = userRequest.toUser();
        validateUserId(user.getUserId());
        dataBase.addUser(user);
        return user;
    }

    private void validateUserId(String userId) {
        if (Objects.nonNull(dataBase.findUserById(userId))) {
            String validateMessage = "userID가 중복되었습니다.";
            LOGGER.error("{} : {}", validateMessage, userId);
            throw new IllegalArgumentException(validateMessage);
        }
    }

    public boolean login(LoginRequest loginRequest) {
        User foundUser = dataBase.findUserById(loginRequest.getUserId());
        return Objects.nonNull(foundUser) && foundUser.isSamePassword(loginRequest.getPassword());
    }

    public List<User> findAllUser() {
        return new ArrayList<>(dataBase.findAll());
    }

    public String createSessionById(String userId) {
        User foundUser = dataBase.findUserById(userId);
        HttpSession httpSession = new HttpSessionImpl();
        String sessionId = httpSession.getId();
        httpSession.setAttribute(ATTRIBUTE_KEY_USER, foundUser);
        httpSessions.saveSession(httpSession);
        return sessionId;
    }
}
