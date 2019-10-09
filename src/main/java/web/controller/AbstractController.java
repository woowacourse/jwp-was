package web.controller;

import webserver.message.HttpMethod;
import webserver.message.exception.NotFoundFileException;
import webserver.message.request.Request;
import webserver.message.response.Response;
import webserver.view.ModelAndView;
import webserver.view.View;

import java.util.Objects;

public class AbstractController implements Controller {

    @Override
    public void service(final Request request, final Response response) {
        ModelAndView mav = null;
        if (request.matchesMethod(HttpMethod.GET)) {
            mav = doGet(request, response);
        }
        if (request.matchesMethod(HttpMethod.POST)) {
            mav = doPost(request, response);
        }

        if (Objects.isNull(mav)) {
            throw new NotFoundFileException();
        }

        View view = mav.getView();
        view.render(response, mav.getModels());
    }

    protected ModelAndView doGet(final Request request, final Response response) {
        throw new NotFoundFileException();
    }

    protected ModelAndView doPost(final Request request, final Response response) {
        throw new NotFoundFileException();
    }
}
