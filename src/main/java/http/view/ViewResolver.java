package http.view;

public interface ViewResolver {
    HttpResponse resolve(ModelAndView modelAndView);
}
