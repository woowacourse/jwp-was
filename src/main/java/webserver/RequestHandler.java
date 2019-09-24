package webserver;

import http.NotSupportedHttpMethodException;
import http.request.HttpRequest;
import http.request.RequestFactory;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.resolver.BadRequestException;
import webserver.resolver.NotFoundException;
import webserver.resolver.RequestResolver;

import java.io.DataOutputStream;
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
        HttpRequest httpRequest;
        HttpResponse httpResponse = new HttpResponse();
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            httpRequest = RequestFactory.createHttpRequest(in);
            RequestResolver.route(httpRequest, httpResponse);
            httpResponse.forward(dos);

        } catch (BadRequestException e) {
            logger.error(e.getMessage());
            httpResponse.badRequest();

        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            httpResponse.notFound();

        } catch (NotSupportedHttpMethodException e) {
            logger.error(e.getMessage());
            httpResponse.methodNotAllow();

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
            httpResponse.internalServerError();
        }
    }
}
