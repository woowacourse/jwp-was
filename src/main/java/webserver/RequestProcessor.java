package webserver;

import utils.FileIoUtils;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.function.Function;

public enum RequestProcessor {
    STATIC_INDEX(HttpMethod.GET, "/index.html",
            httpRequest -> staticProcess("./templates/index.html")),
    STATIC_JOIN(HttpMethod.GET, "/user/form.html",
            httpRequest -> staticProcess("./templates/user/form.html")),
    JOIN(HttpMethod.POST, "/user/create",
            httpRequest -> UserService.join(httpRequest.getBody()).toString().getBytes());

    private static final byte[] ERROR_BODY = "Error".getBytes();
    private static final byte[] DEFAULT_BODY = "Hello World".getBytes();

    private HttpMethod httpMethod;
    private String resourcePath;
    private Function<HttpRequest, byte[]> process;

    RequestProcessor(HttpMethod httpMethod, String resourcePath, Function<HttpRequest, byte[]> process) {
        this.httpMethod = httpMethod;
        this.resourcePath = resourcePath;
        this.process = process;
    }

    public static byte[] process(HttpRequest httpRequest) {
        return Arrays.stream(RequestProcessor.values())
                .filter(x -> isSame(x, httpRequest))
                .findAny()
                .map(x -> x.process.apply(httpRequest))
                .orElse(DEFAULT_BODY);
    }

    private static byte[] staticProcess(String filePath) {
        try {
            return FileIoUtils.loadFileFromClasspath("./templates/index.html");
        } catch (IOException | URISyntaxException e) {
            return ERROR_BODY;
        }
    }

    private static boolean isSame(RequestProcessor requestProcessor, HttpRequest httpRequest) {
        return requestProcessor.httpMethod.equals(httpRequest.getHttpMethod())
                && requestProcessor.resourcePath.equals(httpRequest.getResourcePath());
    }
}
