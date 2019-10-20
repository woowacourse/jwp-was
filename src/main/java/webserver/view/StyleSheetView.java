package webserver.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.common.ModelAndView;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public class StyleSheetView implements View {
    private static final Logger log = LoggerFactory.getLogger(StyleSheetView.class);
    private static final String CONTENT_TYPE_CSS = "text/css";

    @Override
    public void render(HttpRequest httpRequest, HttpResponse httpResponse, ModelAndView modelAndView) throws FileNotFoundException {
        httpResponse.forward(httpRequest, getStaticFile(httpRequest), CONTENT_TYPE_CSS);
    }

    @Override
    public String getViewName() {
        return null;
    }

    private byte[] getStaticFile(HttpRequest httpRequest) throws FileNotFoundException {
        String file = httpRequest.getSource();
        try {
            return FileIoUtils.loadFileFromClasspath("./static/" + file);
        } catch (IOException | URISyntaxException e) {
            log.debug("fail to load file", e);
        }
        throw new FileNotFoundException("fail to find file.");
    }
}
