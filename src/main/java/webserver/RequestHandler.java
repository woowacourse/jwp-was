package webserver;

import controller.ControllerFactory;
import controller.core.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import webserver.http.HttpHeaderField;
import webserver.http.HttpVersion;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestFactory;
import webserver.http.request.core.RequestPath;
import webserver.http.response.HttpResponse;
import webserver.http.response.core.ResponseContentType;
import webserver.session.Session;
import webserver.session.SessionManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.stream.Stream;

public class RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequestFactory.create(in);
            HttpResponse httpResponse = new HttpResponse(HttpVersion.HTTP_VERSION_1_1);
//            initSession(httpRequest, httpResponse);

            if (isStaticFile(httpRequest.getRequestPath())) {
                httpResponse.addStatus();
                httpResponse.addHeader(HttpHeaderField.of("Content-Type:"), ResponseContentType.of(httpRequest.getRequestPath()));
                httpResponse.sendResponse(out, httpRequest);
            } else {
                AbstractController controller = ControllerFactory.mappingController(httpRequest, httpResponse);
                controller.service(out, httpRequest, httpResponse);
            }

        } catch (IOException | URISyntaxException e) {
            log.error(e.getMessage());
        }
    }

//    private void initSession(HttpRequest httpRequest, HttpResponse httpResponse) {
//        String JSESSIONID = httpRequest.getHttpCookieId();
//        if (StringUtils.isEmpty(SessionManager.getSession(JSESSIONID))) {
//            Session session = SessionManager.getSession();
//        }
//    }

    private boolean isStaticFile(RequestPath path) {
        return Stream.of(".css", ".js", ".png", ".ico", ".eot", ".svg", ".ttf", ".woff", ".woff2")
                .anyMatch(extension -> path.getPath().contains(extension));
    }
}
