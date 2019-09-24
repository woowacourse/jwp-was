package webserver;

import controller.Controller;
import controller.exception.HttpMethodNotAllowedException;
import controller.exception.NotFoundUserIdException;
import controller.exception.URINotFoundException;
import http.HttpRequest;
import http.HttpResponse;
import http.MimeType;
import http.exception.NotFoundMethodException;
import model.exception.InvalidPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import view.RedirectView;
import view.View;
import webserver.exception.InvalidUriException;
import webserver.exception.NotFoundResourceException;

import java.io.IOException;
import java.net.URISyntaxException;

import static http.HttpHeader.CONTENT_TYPE;

public class DispatcherServlet {
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private static final String TEMPLATES_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

    public static void doDispatch(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        try {
            if (httpRequest.isStaticRequest()) {
                handleStaticRequest(httpRequest, httpResponse);
                return;
            }

            Controller controller = HandlerMapping.handle(httpRequest);
            View view = controller.service(httpRequest, httpResponse);
            view.render(httpRequest, httpResponse);
        } catch (NotFoundMethodException | HttpMethodNotAllowedException e) {
            log.error(e.getMessage());
            httpResponse.setStatus(405);
        } catch (NotFoundResourceException | InvalidUriException | URINotFoundException e) {
            log.error(e.getMessage());
            httpResponse.setStatus(404);
        } catch (InvalidPasswordException | NotFoundUserIdException e) {
            log.error(e.getMessage());
            httpResponse.addHeader("Set-Cookie", "logined=false; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT");
            View view = new RedirectView("user/login_failed.html");
            view.render(httpRequest, httpResponse);
        } catch (Exception e) {
            log.error(e.getMessage());
            httpResponse.setStatus(500);
        }
    }

    private static void handleStaticRequest(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String path = STATIC_PATH + httpRequest.getUri();
        if (!FileIoUtils.isExistFile(path)) {
            path = TEMPLATES_PATH + httpRequest.getUri();
            if (!FileIoUtils.isExistFile(path)) {
                throw new NotFoundResourceException();
            }
        }
        byte[] body = FileIoUtils.loadFileFromClasspath(path);

        httpResponse.setStatus(200);
        httpResponse.addHeader(CONTENT_TYPE, MimeType.of(httpRequest.getUri()));
        httpResponse.setBody(body);
    }
}