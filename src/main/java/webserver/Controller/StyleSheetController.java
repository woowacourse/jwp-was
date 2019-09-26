package webserver.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.Controller.exception.MethodNotAllowedException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public class StyleSheetController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(StyleSheetController.class);

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws FileNotFoundException {
        byte[] staticFile = getStaticFile(httpRequest);
        if (httpResponse.addBody(staticFile)) {
            httpResponse.addStatusLine(httpRequest, "200", "OK");
            httpResponse.addHeader(HEADER_FIELD_CONTENT_TYPE, CONTENT_TYPE_CSS);
        }
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new MethodNotAllowedException("fail to match method.");
    }

    @Override
    protected byte[] getStaticFile(HttpRequest httpRequest) throws FileNotFoundException {
        String file = httpRequest.getSource();
        try {
            return FileIoUtils.loadFileFromClasspath("./static" + file);
        } catch (IOException | URISyntaxException e) {
            log.debug("fail to load file", e);
        }
        throw new FileNotFoundException("fail to find file.");
    }
}
