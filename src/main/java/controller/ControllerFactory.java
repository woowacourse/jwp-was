package controller;

import controller.core.AbstractController;
import controller.exception.PathNotFoundException;
import webserver.http.request.HttpRequest;
import webserver.http.request.core.RequestMethod;
import webserver.http.request.core.RequestPath;
import webserver.http.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class ControllerFactory {
    private static final Map<String, AbstractController> controllers = new HashMap<>();

    static {
        controllers.put(RequestMethod.GET + "/", resourceController());
        controllers.put(RequestMethod.GET + "/index.html", resourceController());
        controllers.put(RequestMethod.GET + "/user/form.html", resourceController());
        controllers.put(RequestMethod.POST + "/user/create", createUserController());
        controllers.put(RequestMethod.GET + "/user/login.html", resourceController());
        controllers.put(RequestMethod.GET + "/user/login_failed.html", resourceController());
        controllers.put(RequestMethod.GET + "/user/list.html", userListController());
        controllers.put(RequestMethod.POST + "/user/login", loginController());
    }

    private static AbstractController resourceController() {
        return new ResourceController();
    }

    private static AbstractController createUserController() {
        return new CreateUserController();
    }

    private static AbstractController loginController() {
        return new LoginController();
    }

    private static AbstractController userListController() {
        return new UserListController();
    }

    public static AbstractController mappingController(HttpRequest httpRequest) {
        RequestMethod method = httpRequest.getRequestMethod();
        RequestPath path = httpRequest.getRequestPath();
        try {
            return controllers.get(method.getMethod() + path.getPath());
        } catch (NullPointerException e) {
            throw new PathNotFoundException();
        }
    }
}