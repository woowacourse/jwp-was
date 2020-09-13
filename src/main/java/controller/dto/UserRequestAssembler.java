package controller.dto;

import java.util.Map;

public class UserRequestAssembler {

    public static UserRequest assemble(Map<String, String> keyValues) {
        String userId = keyValues.get("userId");
        String password = keyValues.get("password");
        String name = keyValues.get("name");
        String email = keyValues.get("email");

        return new UserRequest(userId, password, name, email);
    }
}
