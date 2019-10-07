package webserver.router;

import webserver.pageprovider.*;

public class RouterFactory {
    private static Router GLOBAL_ROUTER;

    // 여기서 모든 서버에서 공유할 라우터를 설정
    static {
        OrderedRouter orderedRouter = OrderedRouter.getInstance();

        Router basicRouter = BasicRouter.getInstance()
                .addPageProvider(pattern -> pattern.equals("/user/create"), HttpMethodPageProvider.withPost(new UserSignUpPageProvider()))
                .addPageProvider(pattern -> pattern.equals("/user/list"), HttpMethodPageProvider.withGet(new UserListPageProvider()))
                .addPageProvider(pattern -> pattern.equals("/user/profile"), HttpMethodPageProvider.withGet(new UserProfilePageProvider()))
                .addPageProvider(pattern -> pattern.equals("/user/logout"), HttpMethodPageProvider.withGet(new LogoutPageProvider()))
                .addPageProvider(pattern -> pattern.equals("/user/login"), HttpMethodPageProvider.withPost(new LoginPageProvider()));

        orderedRouter.pushBack(FileServerRouter.getInstance());
        orderedRouter.pushBack(basicRouter);

        GLOBAL_ROUTER = orderedRouter;
    }

    public static Router getRouter() {
        return GLOBAL_ROUTER;
    }
}
