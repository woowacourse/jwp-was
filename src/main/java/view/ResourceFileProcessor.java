package view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.RequestHandler;
import webserver.http.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceFileProcessor implements ViewProcessor {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public void resolve(ModelAndView modelAndView) throws IOException, URISyntaxException {
        if (modelAndView.getView() == null) {
            modelAndView.setByteView(FileIoUtils.loadFileFromClasspath("./templates/error.html"));
        }
        modelAndView.setByteView(FileIoUtils.loadFileFromClasspath(modelAndView.getView()));
    }

    @Override
    public boolean isMapping(ModelAndView modelAndView) {
        return modelAndView.isResourceFileView();
    }
}
