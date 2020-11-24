package webserver.controller;

import model.UserService;
import webserver.http.Parameters;
import webserver.http.URL;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;

public class UserCreateController extends AbstractController {
    private static final String REDIRECT_LOCATION = "/index.html";

    public UserCreateController() {
        this.paths = Collections.singletonList("user/create");
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        URL url = httpRequest.getUrl();
        Map<String, String> queryParameters = url.getQueryParameters();
        UserService.create(queryParameters);

        httpResponse.forward(REDIRECT_LOCATION);
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Parameters parameters = httpRequest.getBodyParameters();
        Map<String, String> bodyParameters = parameters.getParameters();
        UserService.create(bodyParameters);

        httpResponse.sendRedirect(REDIRECT_LOCATION);
    }
}
