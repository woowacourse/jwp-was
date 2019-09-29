package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.request.HttpRequest;
import webserver.controller.response.HttpResponse;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream inputStream = connection.getInputStream(); OutputStream outputStream = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(inputStream);

            Router router = Router.getInstance();
            HttpResponse httpResponse = router.serve(httpRequest);
            //TemplateEngine.covert(modelAndView);
            Renderer renderer = Renderer.getInstance();
            renderer.render(outputStream, httpResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
