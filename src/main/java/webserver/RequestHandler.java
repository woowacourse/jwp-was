package webserver;

import controller.Controller;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseResolver;
import http.response.ResponseStatus;
import http.response.view.DefaultView;
import http.response.view.ErrorView;
import http.response.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.UrlNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream();
             HttpRequest httpRequest = new HttpRequest(in);
             HttpResponse httpResponse = new HttpResponse(out)) {

            View view = service(httpRequest, httpResponse);
            ResponseResolver.resolve(view, httpResponse);

        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new NetworkException("네트워크 예외");
        }

    }

    private View service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Controller controller = ControllerContainer.findController(httpRequest.getPath());

        if (controller != null) {
            return controller.service(httpRequest, httpResponse);
        }

        return resolveFiles(httpRequest);
    }

    private View resolveFiles(HttpRequest httpRequest) throws IOException {
        try {
            return new DefaultView(httpRequest.getPath());
        } catch (URISyntaxException | UrlNotFoundException e) {
            return new ErrorView(ResponseStatus.NOT_FOUND, "Not Found");
        }
    }
}
