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

    public static AbstractController mappingController(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestMethod method = httpRequest.getRequestMethod();
        RequestPath path = httpRequest.getRequestPath();
        try {
            return controllers.get(method.getMethod() + path.getPath()).init(httpRequest, httpResponse);
        } catch (NullPointerException e) {
            throw new PathNotFoundException();
        }
    }
}