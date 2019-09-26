package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.Controller.Controller;
import webserver.Controller.exception.MethodNotAllowedException;
import webserver.Controller.exception.NotFoundException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

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

        HttpRequest httpRequest = null;
        HttpResponse httpResponse = new HttpResponse();

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);

            httpRequest = HttpRequest.of(in);
            Controller controller = UrlMapper.getController(httpRequest);
            controller.service(httpRequest, httpResponse);
            httpResponse.render(dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
            httpResponse.addStatusLine(httpRequest, "500", "Internal Server error");
        } catch (NotFoundException e) {
            logger.debug(e.getMessage());
            httpResponse.addStatusLine(httpRequest, "404", "Not Found");
        } catch (MethodNotAllowedException e) {
            logger.debug(e.getMessage());
            httpResponse.addStatusLine(httpRequest, "405", "Method Not Allowed");
        }
    }
}
