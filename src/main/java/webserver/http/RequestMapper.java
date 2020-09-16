package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import web.PageController;
import web.UserController;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class RequestMapper {
    private static final Map<String, Function<HttpRequest, HttpResponse>> mapper = new HashMap<>();

    static {
        mapper.put("/user/create", httpRequest -> new UserController().createUser(httpRequest));
    }

    private RequestMapper() {
    }

    public static HttpResponse mapPage(HttpRequest httpRequest) {
        return Optional.of(httpRequest.getHttpStartLine().getUrl())
            .map(mapper::get)
            .map(el -> el.apply(httpRequest))
            .orElseGet(() -> new PageController().viewFile(httpRequest));
    }
}
