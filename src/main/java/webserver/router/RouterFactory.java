package webserver.router;

import webserver.controller.*;

public class RouterFactory {
    private static Router GLOBAL_ROUTER;

    // 여기서 모든 서버에서 공유할 라우터를 설정
    static {
        OrderedRouter orderedRouter = OrderedRouter.getInstance();

        Router basicRouter = BasicRouter.getInstance()
                .addController(pattern -> pattern.equals("/user/create"), new UserController())
                .addController(pattern -> pattern.equals("/user/list"), new UserListController())
                .addController(pattern -> pattern.equals("/user/profile"), new UserProfileController())
                .addController(pattern -> pattern.equals("/user/logout"), new LogoutController())
                .addController(pattern -> pattern.equals("/user/login"), new LoginController());

        // register routers in order
        orderedRouter.pushBack(FileServerRouter.getInstance());
        orderedRouter.pushBack(basicRouter);

        GLOBAL_ROUTER = orderedRouter;
    }

    public static Router getRouter() {
        return GLOBAL_ROUTER;
    }
}
