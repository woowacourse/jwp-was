import com.google.common.collect.Maps;
import controller.CreateUserController;
import controller.ListUserController;
import controller.LoginController;
import webserver.WebServer;
import webserver.controller.DefaultController;
import webserver.controller.RequestHandlerMapping;

public class Application {

    public static void main(String[] args) throws Exception{
        RequestHandlerMapping requestHandlerMapping = new RequestHandlerMapping(Maps.newHashMap());
        initRequestHandler(requestHandlerMapping);

        WebServer webServer = new WebServer(requestHandlerMapping);
        webServer.run(new String[] {});
    }

    private static void initRequestHandler(RequestHandlerMapping requestHandlerMapping) {
        requestHandlerMapping.putController("/user/list.html", new ListUserController());
        requestHandlerMapping.putController("/user/create", new CreateUserController());
        requestHandlerMapping.putController("/user/login", new LoginController());
        requestHandlerMapping.putController("/", DefaultController.getInstance());
    }
}
