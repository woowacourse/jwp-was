package webserver;

import controller.Controller;
import controller.exception.HttpRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.common.HttpStatus;
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
            Renderer.render(dos, httpResponse);
        } catch (IOException e) {
            logger.error("IOException : {}", e.getMessage());
            httpResponse.setStatusLine(httpRequest, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (HttpRequestException e) {
            logger.debug("HttpException : {}", e.getMessage());
            httpResponse.setStatusLine(httpRequest, HttpStatus.valueOfCode(e.getCode()));
        }
    }
}
