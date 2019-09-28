package webserver.view;

import webserver.response.ModelAndView;

public interface TemplateEngine {
    String render(ModelAndView modelAndView);
}
