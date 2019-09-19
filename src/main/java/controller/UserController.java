package controller;

public class UserController {
    public static UserController getInstance() {
        return UserControllerHolder.INSTANCE;
    }

    private static class UserControllerHolder {
        private static final UserController INSTANCE = new UserController();
    }
}
