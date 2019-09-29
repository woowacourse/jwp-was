package file;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.exception.NotMatchUrlException;
import webserver.support.PathHandler;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileContainer {
    private static final Logger logger = LoggerFactory.getLogger(FileContainer.class);

    public boolean process(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        try {
            String absoluteUrl = PathHandler.path(httpRequest.getResourcePath());
            logger.debug("static file path : {}", absoluteUrl);
            httpResponse.forward(FileIoUtils.loadFileFromClasspath(absoluteUrl));
            return true;
        } catch (NotMatchUrlException e) {
            return false;
        }
    }
}
