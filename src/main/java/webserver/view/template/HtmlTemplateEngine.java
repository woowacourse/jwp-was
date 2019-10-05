package webserver.view.template;

import webserver.view.ModelAndView;

public interface HtmlTemplateEngine {
    byte[] render(ModelAndView modelAndView);
}
