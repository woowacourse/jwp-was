package view;

import webserver.http.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ViewProcessor {
    boolean isMapping(ModelAndView modelAndView);

    void resolve(ModelAndView modelAndView) throws IOException, URISyntaxException;
}
