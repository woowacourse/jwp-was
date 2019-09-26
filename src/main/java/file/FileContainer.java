package file;

import http.HttpResponse;
import http.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.exception.NotMatchUrlException;
import webserver.support.PathHandler;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileContainer {
    private static final Logger logger = LoggerFactory.getLogger(FileContainer.class);

    public boolean process(
            HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        try {
            String absoluteUrl = PathHandler.path(httpRequest.getResourcePath());
            logger.debug("정적 파일 경로 : {}", absoluteUrl);
            httpResponse.forward(absoluteUrl);
            return true;
        } catch (NotMatchUrlException e) {
            return false;
        }
    }
}
