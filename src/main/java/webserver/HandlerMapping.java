package webserver;

import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.staticfile.StaticFileController;
import webserver.staticfile.StaticFileMatcher;
import webserver.user.UserController;

import java.util.Arrays;
import java.util.function.Function;

public enum HandlerMapping {
    JOIN(HttpMethod.POST, "/user/create", httpRequest -> UserController.doPost(httpRequest));

    private HttpMethod httpMethod;
    private String resourcePath;
    private Function<HttpRequest, HttpResponse> process;

    HandlerMapping(HttpMethod httpMethod, String resourcePath, Function<HttpRequest, HttpResponse> process) {
        this.httpMethod = httpMethod;
        this.resourcePath = resourcePath;
        this.process = process;
    }

    public static HttpResponse mapping(HttpRequest httpRequest) {
        if (StaticFileMatcher.isStaticFileResourcePath(httpRequest.getResourcePath())) {
            return StaticFileController.processStaticFile(httpRequest);
        }
        return Arrays.stream(HandlerMapping.values())
                .filter(x -> isSame(x, httpRequest))
                .findAny()
                .map(x -> x.process.apply(httpRequest))
                .orElseThrow(() -> new IllegalArgumentException("잘못된 요청입니다."));
    }

    private static boolean isSame(HandlerMapping handlerMapping, HttpRequest httpRequest) {
        return handlerMapping.httpMethod.equals(httpRequest.getHttpMethod())
                && handlerMapping.resourcePath.equals(httpRequest.getResourcePath());
    }
}
