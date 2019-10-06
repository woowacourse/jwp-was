package webserver.router;

import webserver.controller.LoginController;
import webserver.controller.LogoutController;
import webserver.controller.PageTemplateController;
import webserver.pageprovider.UserListPageProvider;
import webserver.pageprovider.UserProfilePageProvider;
import webserver.pageprovider.UserSignUpPageProvider;

public class RouterFactory {
    private static Router GLOBAL_ROUTER;

    // 여기서 모든 서버에서 공유할 라우터를 설정
    static {
        OrderedRouter orderedRouter = OrderedRouter.getInstance();

        Router basicRouter = BasicRouter.getInstance()
                .addController(pattern -> pattern.equals("/user/create"), PageTemplateController.ofPostPageProvider(new UserSignUpPageProvider()))
                .addController(pattern -> pattern.equals("/user/list"), PageTemplateController.ofGetPageProvider(new UserListPageProvider()))
                .addController(pattern -> pattern.equals("/user/profile"), PageTemplateController.ofGetPageProvider(new UserProfilePageProvider()))
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
