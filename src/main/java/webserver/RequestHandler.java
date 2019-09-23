package webserver;

import controller.AbstractController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import http.response.view.DefaultView;
import http.response.view.ErrorView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.UrlNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Optional;

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

            service(httpRequest, httpResponse);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

    private void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Optional<AbstractController> maybeController = ControllerContainer.findController(httpRequest.getPath());
        try {
            if (maybeController.isPresent()) {
                maybeController.get().service(httpRequest, httpResponse);
                return;
            }
            httpResponse.render(new DefaultView(httpRequest.getPath()));
        } catch (URISyntaxException | UrlNotFoundException e) {
            httpResponse.render(new ErrorView(ResponseStatus.NOT_FOUND, "Not Found"));
        }
    }
}
