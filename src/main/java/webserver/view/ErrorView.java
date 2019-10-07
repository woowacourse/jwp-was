package webserver.view;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import java.io.IOException;

public class ErrorView implements View {
    private final HttpStatus httpStatus;
    private final String message;

    public ErrorView(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public void render(HttpRequest request, HttpResponse response) throws IOException {
        response.error(httpStatus, message);
    }
}
