package controller;

import http.request.Request;
import http.response.RedirectResponse;
import http.response.Response;
import service.UserService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PostUserController extends AbstractController {
    private UserService userService = UserService.getInstance();

    @Override
    public boolean isMapping(Request request) {
        return ("POST /user/create".equals("POST " + request.getUrl().getUrlPath()));
    }

    @Override
    public Response createResponse(Request request) {
        if (isMapping(request)) {
            return createPostResponse(request);
        }
        return new PostUserController().createResponse(request);
    }

    @Override
    public Response createPostResponse(Request request) {
        Map<String, String> params = new HashMap<>();
        String queryParams = request.getRequestInformation().extractQueryParameters();
        String[] tokens = queryParams.split("&");
        Arrays.stream(tokens)
                .forEach(token -> {
                    String[] keyValues = token.split("=");
                    params.put(keyValues[0], keyValues[1]);
                });
        userService.createUser(params);
        return new RedirectResponse();
    }
}
