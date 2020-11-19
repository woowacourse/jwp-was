package webserver.controller;

import model.UserService;
import webserver.http.Body;
import webserver.http.HttpHeaders;
import webserver.http.Parameters;
import webserver.http.URL;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseStartLine;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class UserCreateController extends AbstractController {
    private static final String REDIRECT_PATH = "/index.html";

    public UserCreateController() {
        this.paths = Collections.singletonList("user/create");
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        URL url = httpRequest.getUrl();
        Map<String, String> queryParameters = url.getQueryParameters();
        UserService.create(queryParameters);

        HttpResponseStartLine httpResponseStartLine = response200StartLine();
        HttpHeaders httpResponseHeader = HttpHeaders.emptyHeaders();
        Body httpResponseBody = Body.emptyBody();

        httpResponse.forward(httpResponseStartLine, httpResponseHeader, httpResponseBody);
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Parameters parameters = httpRequest.getBodyParameters();
        Map<String, String> bodyParameters = parameters.getParameters();
        UserService.create(bodyParameters);

        HttpResponseStartLine httpResponseStartLine = response302StartLine();
        HttpHeaders httpResponseHeaders = responseWithLocation(REDIRECT_PATH);
        Body httpResponseBody = Body.emptyBody();

        httpResponse.forward(httpResponseStartLine, httpResponseHeaders, httpResponseBody);
    }
}
