package jwp.was.webapplicationserver.controller.dto;

import java.util.Map;

public class LoginRequestAssembler {

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";

    private LoginRequestAssembler() {
    }

    public static LoginRequest assemble(Map<String, String> keyValues) {
        String userId = keyValues.get(USER_ID);
        String password = keyValues.get(PASSWORD);

        return new LoginRequest(userId, password);
    }
}
