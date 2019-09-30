package webserver;

import controller.Controller;
import http.HttpRequest;
import http.HttpResponse;
import http.MimeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import view.View;
import webserver.exception.AbstractHttpException;
import webserver.exception.NotFoundResourceException;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

public class DispatcherServlet {
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private static final String TEMPLATES_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

    public void doDispatch(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        try {
            if (httpRequest.isStaticRequest()) {
                handleStaticRequest(httpRequest, httpResponse);
                return;
            }

            Controller controller = HandlerMapping.handle(httpRequest);
            View view = controller.service(httpRequest, httpResponse);
            view.render(httpRequest, httpResponse);
        } catch (AbstractHttpException e) {
            log.error(e.getMessage());
            httpResponse.sendError(e);
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
    }

    private void handleStaticRequest(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String path = validatePath(httpRequest);
        byte[] body = FileIoUtils.loadFileFromClasspath(path);

        httpResponse.setStatus(200);
        httpResponse.addHeader(CONTENT_TYPE, MimeType.of(httpRequest.getUri()));
        httpResponse.setBody(body);
    }

    private String validatePath(HttpRequest httpRequest) {
        String path = STATIC_PATH + httpRequest.getUri();
        if (!FileIoUtils.isExistFile(path)) {
            path = TEMPLATES_PATH + httpRequest.getUri();
            if (!FileIoUtils.isExistFile(path)) {
                throw new NotFoundResourceException();
            }
        }
        return path;
    }
}