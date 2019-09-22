package factory;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class UserFactory {
    private static final Logger logger = LoggerFactory.getLogger(UserFactory.class);

    public static User of(Map<String, String> signUpDataSet) {
        return new User(signUpDataSet.get("userId"),signUpDataSet.get("password"),signUpDataSet.get("name"), signUpDataSet.get("email"));
    }
}
