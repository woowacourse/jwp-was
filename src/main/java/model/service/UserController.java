package model.service;

import model.db.DataBase;
import model.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    public static void saveUser(Map<String, String> params, DataOutputStream dos) {
        User user = new User(
                params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email")
        );
        DataBase.addUser(user);
        logger.debug("save userId : {}", user.getUserId());

        response302Header(dos, "/index.html");
    }

    private static void response302Header(DataOutputStream dos, String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: " + redirectUrl + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
