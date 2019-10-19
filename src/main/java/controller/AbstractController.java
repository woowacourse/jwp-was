package controller;

import controller.exception.HttpRequestException;
import webserver.common.HttpStatus;
import webserver.common.ModelAndView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.view.View;

import java.io.FileNotFoundException;
import java.util.Objects;

import static webserver.request.RequestHeader.SESSION_KEY;

public abstract class AbstractController implements Controller {
    private static final String REDIRECT_PREFIX = "redirect:";

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws FileNotFoundException {
        if (httpRequest.isGet()) {
            resolveGet(httpRequest, httpResponse);
        }

        if (httpRequest.isPost()) {
            resolvePost(httpRequest, httpResponse);
        }
    }

    private void resolveGet(HttpRequest httpRequest, HttpResponse httpResponse) throws FileNotFoundException {
        ModelAndView modelAndView = doGet(httpRequest, httpResponse);
        View view = modelAndView.getView();
        view.render(httpRequest, httpResponse, modelAndView);
    }

    private void resolvePost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String path = doPost(httpRequest, httpResponse);
        if (path.startsWith(REDIRECT_PREFIX)) {
            httpResponse.sendRedirect(httpRequest, path);
        }
        if (Objects.nonNull(httpRequest.getSessionId())) {
            httpResponse.addCookie(SESSION_KEY, httpRequest.getSessionId());
        }
    }

    protected ModelAndView doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new HttpRequestException(HttpStatus.METHOD_NOT_ALLOWED);
    }

    protected String doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new HttpRequestException(HttpStatus.METHOD_NOT_ALLOWED);
    }
}
