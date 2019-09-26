package view.excpetion;

import view.View;
import view.ViewResolver;
import webserver.http.HttpStatus;
import webserver.http.response.HttpResponse;

public class ExceptionViewResolver implements ViewResolver {

    public View resolveView(final HttpResponse httpResponse) {
        return resolveView(HttpStatus.NOT_FOUND, httpResponse);
    }

    public View resolveView(final HttpStatus httpStatus, final HttpResponse httpResponse) {
        httpResponse.sendError(httpStatus);
        return new ExceptionView();
    }
}
