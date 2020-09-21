package webserver;

import webserver.controller.StaticFileController;
import webserver.controller.UserController;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

import java.util.Arrays;
import java.util.function.Function;

public enum HandlerMapping {
    STATIC_INDEX(HttpMethod.GET, "/index.html",
            httpRequest -> StaticFileController.staticProcess("./templates/index.html")),
    STATIC_JOIN(HttpMethod.GET, "/user/form.html",
            httpRequest -> StaticFileController.staticProcess("./templates/user/form.html")),
    JOIN(HttpMethod.POST, "/user/create",
            httpRequest -> UserController.doPost(httpRequest));

    private static final String ERROR_PAGE = "./templates/error.html";

    private HttpMethod httpMethod;
    private String resourcePath;
    private Function<HttpRequest, HttpResponse> process;

    HandlerMapping(HttpMethod httpMethod, String resourcePath, Function<HttpRequest, HttpResponse> process) {
        this.httpMethod = httpMethod;
        this.resourcePath = resourcePath;
        this.process = process;
    }

    public static HttpResponse mapping(HttpRequest httpRequest) {
        return Arrays.stream(HandlerMapping.values())
                .filter(x -> isSame(x, httpRequest))
                .findAny()
                .map(x -> x.process.apply(httpRequest))
                .orElse(StaticFileController.staticProcess(ERROR_PAGE, HttpStatus.BAD_REQUEST));
    }

    private static boolean isSame(HandlerMapping handlerMapping, HttpRequest httpRequest) {
        return handlerMapping.httpMethod.equals(httpRequest.getHttpMethod())
                && handlerMapping.resourcePath.equals(httpRequest.getResourcePath());
    }
}
