package http.request;

import http.controller.Controller;
import http.response.HttpResponse;

import java.util.Arrays;
import java.util.function.BiFunction;

public enum RequestMethod {
    GET(Controller::get),
    POST(Controller::post);

    private final BiFunction<Controller, HttpRequest, HttpResponse> function;

    RequestMethod(BiFunction<Controller, HttpRequest, HttpResponse> function) {
        this.function = function;
    }

    public static RequestMethod of(String method) {
        return Arrays.stream(values())
                .filter(t -> t.name().equals(method))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported method"));
    }

    public HttpResponse extractResponse(Controller controller, HttpRequest httpRequest) {
        return this.function.apply(controller, httpRequest);
    }
}
