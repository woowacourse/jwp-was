package webserver;

import controller.Controller;
import http.HttpRequest;
import http.HttpResponse;
import http.MimeType;
import utils.FileIoUtils;
import view.View;
import view.ViewResolver;
import webserver.exception.NotFoundResourceException;

import java.io.IOException;
import java.net.URISyntaxException;

import static http.HttpHeader.CONTENT_TYPE;

public class DispatcherServlet {
    private static final String TEMPLATES_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

    public static void doDispatch(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (httpRequest.isStaticRequest()) {
            handleStaticRequest(httpRequest, httpResponse);
            return;
        }

        Controller controller = HandlerMapping.handle(httpRequest);
        View view = controller.service(httpRequest, httpResponse);
        if (!view.isRedirectView()) {
            httpResponse.setBody(ViewResolver.resolve(view.getViewName()));
        }
        view.render(httpRequest, httpResponse);
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