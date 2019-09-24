package webserver.router;

import webserver.controller.StaticController;
import webserver.controller.TemplatesController;
import webserver.controller.UserController;

import java.util.Arrays;

public class RouterFactory {
    private static Router GOLOBAL_ROUTER;

    static {
        // 여기서 모든 서버에서 공유할 라우터를 설정
        Router basicRouter = BasicRouter.getInstance()
                .addController(path -> path.contains(".html"), new TemplatesController())
                .addController(path -> path.contains(".css"), new StaticController())
                .addController(path -> path.contains(".js"), new StaticController())
                .addController(pattern -> pattern.equals("/user/create"), new UserController());

        GOLOBAL_ROUTER = new OrderedRouter(Arrays.asList(
                basicRouter));
    }

    public static Router getRouter() {
        return GOLOBAL_ROUTER;
    }
}
