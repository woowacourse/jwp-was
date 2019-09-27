package webserver.resolver;

import utils.FileIoUtils;
import webserver.view.ModelAndView;
import webserver.view.View;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileResolver implements Resolver {
    @Override
    public View resolve(ModelAndView modelAndView) throws IOException, URISyntaxException {
        String viewName = modelAndView.getViewName();
        if (viewName.contains(".html")) {
            return new View(FileIoUtils.loadHtmlFile(viewName));
        }
        return new View(FileIoUtils.loadStaticFile(viewName));
    }
}
