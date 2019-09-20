package controller;

import http.request.Request;
import http.response.RedirectResponse;
import http.response.Response;
import http.response.ResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import java.util.HashMap;
import java.util.Map;

public class PostDataController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(PostDataController.class);

    private UserService userService;
    private Request request;
    private final Map<String, String> parameters = new HashMap<>();

    public PostDataController(Request request, Map<String, String> parameters) {
        this.request = request;
        this.parameters.putAll(parameters);
        userService = UserService.getInstance();
    }

    public void createUser(Request request) {
        userService.createUser(parameters);
        ResponseFactory.getResponse(request);
    }

    @Override
    public Response createResponse() {
        createUser(request);
        return new RedirectResponse();
    }
}
