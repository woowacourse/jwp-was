package jwp.was.webapplicationserver.controller.dto;

import java.util.Map;

public class UserRequestAssembler {

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    private UserRequestAssembler() {
    }

    public static UserRequest assemble(Map<String, String> keyValues) {
        String userId = keyValues.get(USER_ID);
        String password = keyValues.get(PASSWORD);
        String name = keyValues.get(NAME);
        String email = keyValues.get(EMAIL);

        return new UserRequest(userId, password, name, email);
    }
}
