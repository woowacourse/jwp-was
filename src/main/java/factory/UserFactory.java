package factory;

import exception.InvalidSignUpDataException;
import model.User;

import java.util.Map;

public class UserFactory {
    private static final int VALID_SIGNUP_DATA_SIZE = 4;

    public static User of(Map<String, String> signUpDataSet) {
        if (signUpDataSet.size() != VALID_SIGNUP_DATA_SIZE) {
            throw new InvalidSignUpDataException();
        }
        return new User(signUpDataSet.get("userId"), signUpDataSet.get("password"), signUpDataSet.get("name"), signUpDataSet.get("email"));
    }
}
