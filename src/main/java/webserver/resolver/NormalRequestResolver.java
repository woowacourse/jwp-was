package webserver.resolver;

import controller.Controller;
import controller.LoginController;
import controller.UserController;
import controller.UserListController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView;
import view.ViewResolver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NormalRequestResolver {
    private static final Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/user/create", new UserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/list", new UserListController());
    }

    static void resolve(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String path = httpRequest.getPath();
        Controller controller = Optional.ofNullable(controllers.get(path)).orElseThrow(BadRequestException::new);

        ModelAndView modelAndView = controller.service(httpRequest, httpResponse);
        byte[] body = ViewResolver.render(modelAndView);

        if(modelAndView.isRedirect()){
            httpResponse.redirect(modelAndView.getView());
            return;
        }
        httpResponse.okResponse("html", body);
    }
}
