package http.view;

import http.model.HttpResponse;

public interface Resolver {
    HttpResponse resolve(ModelAndView modelAndView);
}
