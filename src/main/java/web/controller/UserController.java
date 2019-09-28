package web.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import domain.db.DataBase;
import domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Responsive;
import webserver.storage.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserController() {
    }

    private static class UserControllerHolder {
        private static final UserController INSTANCE = new UserController();
    }

    public static UserController getInstance() {
        return UserControllerHolder.INSTANCE;
    }

    public Responsive goForm() {
        return (request, response) -> {
            response.setContentType("text/html");
            response.forward("/user/form.html");
        };
    }

    public Responsive createUser() {
        return (request, response) -> {
            User user = new User(
                    request.getBody("userId"),
                    request.getBody("password"),
                    request.getBody("name"),
                    request.getBody("email")
            );
            logger.debug("user : {}", user);
            DataBase.addUser(user);

            response.sendRedirect("/");
        };
    }

    public Responsive goLoginForm() {
        return (request, response) -> {
            response.setContentType("text/html");
            response.forward("/user/login.html");
        };
    }

    public Responsive goLoginFail() {
        return (request, response) -> {
            response.setContentType("text/html");
            response.forward("/user/login_failed.html");
        };
    }

    public Responsive login() {
        return (request, response) -> {
            String userId = request.getBody("userId");
            String password = request.getBody("password");

            User user = DataBase.findUserById(userId);

            if (user.notMatchPassword(password)) {
                response.sendRedirect("/login-fail");
                return;
            }

            HttpSession httpSession = request.getSession();
            response.addHeader("Set-Cookie", "JSESSIONID=" + httpSession.getId() + "; Path=/");
            httpSession.setAttribute("user", user);
            response.sendRedirect("/");
        };
    }

    public Responsive goUserList() {
        return (request, response) -> {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user == null) {
                response.sendRedirect("/login");
                return;
            }

            Map<String, Object> model = new HashMap<>();
            model.put("users", DataBase.findAll());

            try {
                TemplateLoader loader = new ClassPathTemplateLoader();
                loader.setPrefix("/templates");
                loader.setSuffix(".html");
                Handlebars handlebars = new Handlebars(loader);
                handlebars.registerHelper("plusOne", (context, options) -> (Integer) context + 1);

                Template template = handlebars.compile("user/list");
                String aa = template.apply(model);
                response.templateForward(aa);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    // FIXME: 2019-09-27 Cookie ver.
//    public Responsive login() {
//        return (request, response) -> {
//            String userId = request.getBody("userId");
//            String password = request.getBody("password");
//
//            User user = DataBase.findUserById(userId);
//
//            if (user.checkPassword(password)) {
//                response.addHeader("Set-Cookie", "loggedIn=true; Path=/");
//                response.sendRedirect("/");
//                return;
//            }
//
//            response.addHeader("Set-Cookie", "loggedIn=false;");
//            response.sendRedirect("/login");
//        };
//    }
//
//    public Responsive goUserList() {
//        return (request, response) -> {
//            String cookies = request.getCookie();
//            String[] pieceOfCookie = cookies.split("; ");
//            Map<String, String> cookieMenu = new HashMap<>();
//
//            for (String cookie : pieceOfCookie) {
//                String[] brokenCookie = cookie.split("=");
//                cookieMenu.put(brokenCookie[0], brokenCookie[1]);
//            }
//
//            boolean loggedIn = Boolean.parseBoolean(cookieMenu.get("loggedIn"));
//            if (!loggedIn) {
//                response.sendRedirect("/login");
//                return;
//            }
//
//            Map<String, Object> map = new HashMap<>();
//            map.put("users", DataBase.findAll());
//
//            try {
//                TemplateLoader loader = new ClassPathTemplateLoader();
//                loader.setPrefix("/templates");
//                loader.setSuffix(".html");
//                Handlebars handlebars = new Handlebars(loader);
//                handlebars.registerHelper("coogie", (context, options) -> (Integer) context + 1);
//
//                Template template = handlebars.compile("user/list");
//                String aa = template.apply(map);
//                response.templateForward(aa);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        };
//    }
}
