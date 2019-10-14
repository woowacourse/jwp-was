package http.controller;

import http.model.HttpRequest;
import http.model.HttpResponse;

public class LogoutController implements Controller {
    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        httpRequest.getHttpSession().removeAttribute("user");

        return HttpResponse.builder()
                .sendRedirect("/index.html")
                .build();
    }
}
