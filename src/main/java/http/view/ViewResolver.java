package http.view;

import http.model.HttpResponse;

public interface ViewResolver {
    HttpResponse resolve(ModelAndView modelAndView);
}
