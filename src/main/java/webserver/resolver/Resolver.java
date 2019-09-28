package webserver.resolver;

import webserver.view.ModelAndView;
import webserver.view.View;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Resolver {
    View createView(String viewName) throws IOException, URISyntaxException;
}