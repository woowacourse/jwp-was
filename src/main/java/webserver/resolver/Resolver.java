package webserver.resolver;

import webserver.view.ModelAndView;
import webserver.view.View;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Resolver {
    View resolve(ModelAndView modelAndView) throws IOException, URISyntaxException;
}
