package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.request.Request;
import http.request.RequestMethod;
import http.response.Response;
import http.response.ResponseHeaders;
import http.response.ResponseStatus;
import http.session.Session;
import model.User;
import service.UserService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class UserController implements Controller {
    private List<RequestMethod> allowedMethods = Arrays.asList(RequestMethod.POST, RequestMethod.GET);
    private List<String> allowedUrlPaths = Arrays.asList("/user/create", "/user/list");

    private UserService userService = UserService.getInstance();

    @Override
    public boolean isMapping(ControllerMapper controllerMapper) {
        return (isAllowedMethod(controllerMapper.getRequestMethod())
                && isAllowedUrlPath(controllerMapper.getOriginalUrlPath()));
    }

    @Override
    public void processResponse(Request request, Response response) throws IOException, URISyntaxException {
        if (request.getUrl().getOriginalUrlPath().equals("/user/create")) {
            createUserAndRedirect(request, response);
        }
        if (request.getUrl().getOriginalUrlPath().equals("/user/list")) {
            getUserList(request, response);
        }
    }

    private void getUserList(Request request, Response response) throws IOException, URISyntaxException {
        Session session = request.getSession();
        if (session.getAttriubte("user") != null) {

            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);
            Template template = handlebars.compile("user/list");
            Map<String, List<User>> users = new HashMap<>();
            List<User> userList = new ArrayList<>(DataBase.findAll());
            users.put("users", userList);

            String listPage = template.apply(users);
            response.setResponseStatus(ResponseStatus.OK);
            response.setResponseHeaders(new ResponseHeaders());
            response.addResponseHeaders("Content-Type: ", "text/html");

            response.setResponseBody(listPage.getBytes());
        }

        if (session.getAttriubte("user") == null) {
            response.setResponseStatus(ResponseStatus.FOUND);
            response.setResponseHeaders(new ResponseHeaders());
            response.setEmptyResponseBody();
            response.addResponseHeaders("Location: ", "http://localhost:8080/user/login.html");
        }
    }

    private void createUserAndRedirect(Request request, Response response) {
        userService.createUser(request.getQueryParameters().getQueryParameters());
        response.setResponseStatus(ResponseStatus.FOUND);
        response.setResponseHeaders(new ResponseHeaders());
        response.setEmptyResponseBody();
        response.addResponseHeaders("Location: ", "http://localhost:8080/index.html");
    }

    private boolean isAllowedUrlPath(String originalUrlPath) {
        return allowedUrlPaths.stream()
                .anyMatch(originalUrlPath::contains);
    }

    private boolean isAllowedMethod(RequestMethod requestMethod) {
        return allowedMethods.stream()
                .anyMatch(method -> method == requestMethod);
    }
}
