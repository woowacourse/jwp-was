package controller;

import controller.core.Controller;
import http.request.HttpRequest;
import http.request.core.RequestMethod;
import http.request.core.RequestPath;

import java.util.Arrays;
import java.util.List;

public class HomeController implements Controller {
    private final List<String> allowedMappers = Arrays.asList(
            RequestMethod.valueOf("GET").getMethod() + " ../resources/templates/index.html",
            RequestMethod.valueOf("GET").getMethod() + " ../resources/templates/favicon.ico"
    );


    @Override
    public boolean isMapping(HttpRequest httpRequest) {
        RequestMethod requestMethod = httpRequest.getRequestMethod();
        RequestPath requestPath = httpRequest.getRequestPath();
        String checkValue = requestMethod.getMethod() + " " + requestPath.getFullPath();

        return allowedMappers.stream()
                .anyMatch(checkValue::contains);
    }

    @Override
    public int doController() {
        return 200;
    }


}
