package controller;

import java.util.Objects;
import java.util.function.Function;
import model.general.ContentType;
import model.request.Request;
import model.response.Response;
import service.ApiService;
import service.ResourceService;

public enum Controller {

    RESOURCE_CONTROLLER(request -> {
        try {
            return ResourceService.execute(request);
        } catch (Exception e) { // Todo: refactor
            e.printStackTrace();
        }
        return null;
    }),
    API_CONTROLLER(request -> {
        return ApiService.execute(request);
    });

    private final Function<Request, Response> operation;

    Controller(Function<Request, Response> operation) {
        this.operation = operation;
    }

    public static Controller of(ContentType contentType) {
        if (Objects.nonNull(contentType)) {
            return RESOURCE_CONTROLLER;
        }
        return API_CONTROLLER;
    }

    public Response executeOperation(Request request) {
        return operation.apply(request);
    }
}
