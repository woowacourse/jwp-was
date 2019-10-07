package webserver;

import exception.HttpMethodNotFoundException;
import exception.InvalidSignUpDataException;
import exception.UnregisteredURLException;
import exception.UnsupportedMethodException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.request.HttpRequest;
import webserver.controller.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
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
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            HttpResponse httpResponse = getHttpResponse(inputStream);

            Renderer renderer = Renderer.getInstance();
            renderer.render(dataOutputStream, httpResponse);
        } catch (IOException e) {
            logger.debug(e.getMessage());
        }
    }

    private HttpResponse getHttpResponse(InputStream inputStream) throws IOException {
        HttpResponse httpResponse;
        try {
            HttpRequest httpRequest = new HttpRequest(inputStream); //HttpMethodNotFoundException , UnsupportedMethodException
            Router router = Router.getInstance();
            httpResponse = router.serve(httpRequest);
        } catch (HttpMethodNotFoundException | UnsupportedMethodException e) {
            httpResponse = HttpResponse.badRequest(e.getMessage());
            logger.debug("HttpMethodNotFoundException!!");
        } catch (UnregisteredURLException e) {
            httpResponse = HttpResponse.NotFound(e.getMessage());
            logger.debug("not found!!");
        } catch (InvalidSignUpDataException e) {
            httpResponse = HttpResponse.InternerServerError(e.getMessage());
            logger.debug("interner server error");
        }
        return httpResponse;
    }
}
