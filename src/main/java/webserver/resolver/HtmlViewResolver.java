package webserver.resolver;

import utils.FileIoUtils;
import webserver.view.ModelAndView;
import webserver.view.View;

import java.io.IOException;
import java.net.URISyntaxException;

public class HtmlViewResolver implements Resolver {
    final String PREFIX = "/";
    final String POSTFIX = ".html";

    @Override
    public View resolve(ModelAndView modelAndView) throws IOException, URISyntaxException {
        if (!modelAndView.isViewExists()) {
            return new View();
        }
        return new View(FileIoUtils.loadHtmlFile(PREFIX + modelAndView.getViewName() + POSTFIX));
    }
}
