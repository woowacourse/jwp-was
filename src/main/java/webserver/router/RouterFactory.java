package webserver.router;

import webserver.controller.UserController;
import webserver.controller.UserListController;

public class RouterFactory {
    private static Router GLOBAL_ROUTER;

    // 여기서 모든 서버에서 공유할 라우터를 설정
    static {
        OrderedRouter orderedRouter = OrderedRouter.getInstance();

        Router basicRouter = BasicRouter.getInstance()
                .addController(pattern -> pattern.equals("/user/create"), new UserController())
                .addController(pattern -> pattern.equals("/user/list"), new UserListController());

        // register routers in order
        orderedRouter.pushBack(FileServerRouter.getInstance());
        orderedRouter.pushBack(basicRouter);

        GLOBAL_ROUTER = orderedRouter;
    }

    public static Router getRouter() {
        return GLOBAL_ROUTER;
    }
}
