package webserver;

import db.DataBase;
import http.request.RequestParameter;
import model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {
    private static final String USER_ID = "olaf";
    private static final String PASSWORD = "bmo";
    private static final String NAME = "bhy";
    private static final String EMAIL = "test@gmail.com";
    private static final String CREATE_RESPONSE_URL = "redirect: /index.html";
    private static final String QUERY_STRING =
            "userId=" + USER_ID
                    + "&password=" + PASSWORD
                    + "&name=" + NAME
                    + "&email=" + EMAIL;

    @Test
    void create() {
        UserController userController = UserController.getInstance();
        RequestParameter requestParameter = new RequestParameter(QUERY_STRING);

        User user = new User(USER_ID, PASSWORD, NAME, EMAIL);
        String responseUrl = userController.create(requestParameter);
        assertEquals(DataBase.findUserById(USER_ID), user);
        assertEquals(responseUrl, CREATE_RESPONSE_URL);
    }
}