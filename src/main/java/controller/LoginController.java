package controller;

import db.DataBase;
import http.request.Request;
import http.request.RequestMethod;
import http.response.Response;
import http.response.ResponseHeaders;
import http.response.ResponseStatus;
import http.session.Session;
import http.session.SessionRepository;
import model.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class LoginController implements Controller {
    private List<RequestMethod> allowedMethods = Arrays.asList(RequestMethod.POST);
    private List<String> allowedUrlPaths = Arrays.asList("/user/login");

    @Override
    public boolean isMapping(ControllerMapper controllerMapper) {
        return (isAllowedMethod(controllerMapper.getRequestMethod())
                && isAllowedUrlPath(controllerMapper.getOriginalUrlPath()));
    }

    @Override
    public void processResponse(Request request, Response response) throws IOException, URISyntaxException {
        processLogin(request, response);
    }

    private void processLogin(Request request, Response response) {
        User user = DataBase.findUserById(request.getQueryParameters().getParameter("userId"));

        if (user != null && user.isCorrectPassWord(request.getQueryParameters().getParameter("password"))) {
            response.setResponseStatus(ResponseStatus.FOUND);
            response.setResponseHeaders(new ResponseHeaders());
            response.setEmptyResponseBody();
            response.addResponseHeaders("Location: ", "http://localhost:8080/index.html");
            Session session = request.getSession();
            session.setAttribute("user", user.getUserId());
            response.addResponseHeaders("Set-Cookie: Session-Id=", session.getSessionId() + "; Path=/");
        }

        if (user == null || !user.isCorrectPassWord(request.getQueryParameters().getParameter("password"))) {
            response.setResponseStatus(ResponseStatus.FOUND);
            response.setResponseHeaders(new ResponseHeaders());
            response.setEmptyResponseBody();
            response.addResponseHeaders("Location: ", "http://localhost:8080/user/login_failed.html");
        }
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
