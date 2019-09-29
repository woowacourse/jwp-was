package controller;

import controller.controllermapper.ControllerMapper;
import controller.support.TemplateManager;
import http.request.Request;
import http.request.RequestMethod;
import http.response.Response;
import http.session.Session;
import http.session.sessionkeygenerator.UUIDSessionKeyGenerator;

import java.io.IOException;

public class GetUserListController implements Controller {
    private final RequestMapping requestMapping = new RequestMapping(RequestMethod.GET, "/user/list");

    @Override
    public boolean isMapping(ControllerMapper controllerMapper) {
        return requestMapping.isCorrectMapping(controllerMapper);
    }

    @Override
    public void processResponse(Request request, Response response) throws IOException {
        Session session = request.getSession(new UUIDSessionKeyGenerator());
        if (session.getAttriubte("user") != null) {

            String page = TemplateManager.getTemplateProcessedPage();
            response.ok()
                    .putResponseHeaders("Content-Type: ", "text/html")
                    .body(page.getBytes());
        }

        if (session.getAttriubte("user") == null) {
            response.found()
                    .putResponseHeaders("Location: ", "http://localhost:8080/user/login.html");
        }

    }
}
