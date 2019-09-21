package factory;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class UserFactory {
    private static final Logger logger = LoggerFactory.getLogger(UserFactory.class);

    public static User of(String[] signUpData) {
        HashMap<String, String> signUpDataSet = new HashMap<>();
        signUpDataSet.put("userId", signUpData[0]);
        signUpDataSet.put("password", signUpData[1]);
        signUpDataSet.put("name", signUpData[2]);
        signUpDataSet.put("email", signUpData[3]);

        for (String key : signUpDataSet.keySet()) {
            logger.debug(signUpDataSet.get(key));
        }

        return new User(signUpDataSet.get("userId"),signUpDataSet.get("password"),signUpDataSet.get("name"), signUpDataSet.get("email"));
    }
}
