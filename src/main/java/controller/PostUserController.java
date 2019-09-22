package controller;

import http.request.Request2;
import http.request.RequestMethod;
import http.request.RequestUrlType;
import http.response.FileResponse2;
import http.response.RedirectResponse2;
import http.response.Response2;
import service.UserService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PostUserController extends AbstractController2 {
    private UserService userService = UserService.getInstance();

    @Override
    public boolean isMapping(Request2 request) {
        return ("POST /user/create".equals("POST " + request.getUrl().getUrlPath()));
    }

    @Override
    public Response2 createResponse(Request2 request) {
        if (isMapping(request)) {
            return createPostResponse(request);
        }
        return new PostUserController().createResponse(request);
    }

    @Override
    public Response2 createPostResponse(Request2 request) {
        Map<String, String> params = new HashMap<>();
        String queryParams = request.getRequestInformation().extractQueryParameters();
        String[] tokens = queryParams.split("&");
        Arrays.stream(tokens)
                .forEach(token -> {
                    String[] keyValues = token.split("=");
                    params.put(keyValues[0], keyValues[1]);
                });
        userService.createUser(params);
        return new RedirectResponse2();
    }
}
