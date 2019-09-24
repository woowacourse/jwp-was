package webserver.router;

import webserver.controller.StaticController;
import webserver.controller.TemplatesController;
import webserver.controller.UserController;

public class RouterFactory {
    private static Router GLOBAL_ROUTER;

    // 여기서 모든 서버에서 공유할 라우터를 설정
    static {
        OrderedRouter orderedRouter = OrderedRouter.getInstance();

        Router basicRouter = BasicRouter.getInstance()
                .addController(path -> path.contains(".html"), new TemplatesController())
                .addController(path -> path.contains(".css"), new StaticController())
                .addController(path -> path.contains(".js"), new StaticController())
                .addController(pattern -> pattern.equals("/user/create"), new UserController());

        // register routers in order
        orderedRouter.pushBack(FileServerRouter.getInstance());
        orderedRouter.pushBack(basicRouter);

        GLOBAL_ROUTER = orderedRouter;
    }

    public static Router getRouter() {
        return GLOBAL_ROUTER;
    }
}
