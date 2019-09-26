package webserver.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(AbstractController.class);

    protected static final String HEADER_FIELD_CONTENT_TYPE = "Content-Type";
    protected static final String HEADER_FIELD_CONTENT_LENGTH = "Content-Length";
    protected static final String HEADER_FIELD_LOCATION = "Location";
    protected static final String HEADER_FIELD_ORIGIN = "Origin";
    protected static final String CONTENT_TYPE_HTML = "text/html;charset=utf-8";
    protected static final String CONTENT_TYPE_CSS = "text/css";

    private static final String TEMPLATES_PATH = "./templates/";

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws FileNotFoundException {
        if (httpRequest.isGet()) {
            doGet(httpRequest, httpResponse);
        }
        if (httpRequest.isPost()) {
            doPost(httpRequest, httpResponse);
        }
    }

    protected byte[] getStaticFile(HttpRequest httpRequest) throws FileNotFoundException {
        String file = httpRequest.getSource();
        try {
            return FileIoUtils.loadFileFromClasspath(TEMPLATES_PATH + file);
        } catch (IOException | URISyntaxException e) {
            log.debug("fail to load file", e);
        }
        throw new FileNotFoundException("fail to find file.");
    }

    protected abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws FileNotFoundException;

    protected abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse);
}
