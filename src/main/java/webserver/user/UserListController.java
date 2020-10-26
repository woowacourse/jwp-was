package webserver.user;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ModelAndView;
import view.View;
import webserver.controller.AbstractController;
import webserver.controller.ExceptionHandler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.session.Cookie;
import webserver.http.session.SessionStorage;
import webserver.staticfile.StaticFileMatcher;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getHeader("Cookie") == null) {
            httpResponse.redirect("/user/login");
            return;
        }

        Cookie cookie = (Cookie) httpRequest.getHeader("Cookie");
        if (!SessionStorage.has((String) cookie.get("logined"))) {
            httpResponse.redirect("/user/login");
            return;
        }

        Collection<User> users = UserService.findUsers();
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        String location = "/user/list.html";
        View view = new View(StaticFileMatcher.findStaticFilePath(location));
        httpResponse.ok(new ModelAndView(view, model));
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        logger.info("지원하지 않는 메서드입니다.");
        ExceptionHandler.processException(new NoSuchMethodException("지원하지 않는 메서드입니다."), httpResponse);
    }
}
