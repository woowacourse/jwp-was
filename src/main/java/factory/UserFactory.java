package factory;

import model.User;
import java.util.HashMap;

public class UserFactory {
    public static User of(String[] signUpData) {
        HashMap<String, String> signUpDataSet = new HashMap<>();
        signUpDataSet.put("userId", signUpData[0]);
        signUpDataSet.put("password", signUpData[1]);
        signUpDataSet.put("name", signUpData[2]);
        signUpDataSet.put("email", signUpData[3]);

        for (String key : signUpDataSet.keySet()) {

        }

        return new User(signUpDataSet.get("userId"),signUpDataSet.get("password"),signUpDataSet.get("name"), signUpDataSet.get("email"));
    }
}
