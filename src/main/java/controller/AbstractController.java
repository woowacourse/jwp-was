package controller;

import com.google.common.collect.Lists;
import http.request.Request;
import http.response.Response;
import http.support.HttpMethod;
import http.support.HttpStatus;

import java.util.List;

public abstract class AbstractController implements Controller {
    private List<HttpMethod> supportMethod = Lists.newArrayList(HttpMethod.POST, HttpMethod.GET);

    @Override
    public void service(Request request, Response response) {
        if (request.getMethod().contains(HttpMethod.POST.name())) {
            doPost(request, response);
            return;
        }

        doGet(request, response);

        if (!isSupportMethod(request.getMethod())) {
            response.forward(request.getPath(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    private boolean isSupportMethod(String method) {
        return supportMethod.stream()
                .map(Enum::name)
                .anyMatch(name -> name.equals(method));
    }

    public void doPost(Request request, Response response) {
        response.forward(request.getPath(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    public void doGet(Request request, Response response) {
        response.forward(request.getPath(), HttpStatus.METHOD_NOT_ALLOWED);
    }
}
