package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.controller.ApplicationBusinessException;
import webserver.controller.Controller;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final RequestMapping requestMapping;

    public RequestHandler(Socket connectionSocket, RequestMapping requestMapping) {
        this.connection = connectionSocket;
        this.requestMapping = requestMapping;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpResponse httpResponse = controlRequestAndResponse(HttpRequest.of(in));
            respondToHttpRequest(out, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public HttpResponse controlRequestAndResponse(HttpRequest httpRequest) {
        try {
            Controller controller = requestMapping.getController(httpRequest);
            return controller.service(httpRequest);
        } catch (ApplicationBusinessException e) {
            logger.info(e.getMessage());
            return HttpResponse.businessException(e).build();
        } catch (ClientException e) {
            logger.info(e.getMessage());
            return HttpResponse.clientException(e).build();
        } catch (ServerException e) {
            logger.error(e.getMessage());
            return HttpResponse.internalServerError(e).build();
        }
    }

    private void respondToHttpRequest(OutputStream out, HttpResponse httpResponse) {
        DataOutputStream dos = new DataOutputStream(out);
        httpResponse.respond(dos);
    }
}
