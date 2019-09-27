package webserver.resolver;

import webserver.view.ModelAndView;
import webserver.view.View;

public class HandlebarsViewResolver implements Resolver {

    @Override
    public View resolve(ModelAndView modelAndView) {
        return new View(null);
    }
}
