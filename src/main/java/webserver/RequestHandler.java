package webserver;

import controller.ControllerMapper;
import controller.Controllers;
import controller.exception.NotFoundUrlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequestParser;
import view.ViewResolver;
import webserver.http.HttpSessionManager;
import webserver.http.ModelAndView;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;
    private HttpSessionManager sessionManager;

    public RequestHandler(Socket connectionSocket, HttpSessionManager sessionManager) {
        this.connection = connectionSocket;
        this.sessionManager = sessionManager;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequestParser.parseRequest(new InputStreamReader(in, StandardCharsets.UTF_8), sessionManager);
            HttpResponse httpResponse = HttpResponse.of(httpRequest.getHttpVersion());

            Method method = ControllerMapper.mappingMethod(httpRequest, httpResponse);
            ModelAndView modelAndView = executeMethod(httpRequest, httpResponse, method);
            ViewResolver.resolve(modelAndView);
            OutputStreamHandler.createResponse(httpResponse, modelAndView, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
            throw new NotFoundUrlException(e);
        }
    }

    private ModelAndView executeMethod(HttpRequest httpRequest, HttpResponse httpResponse, Method method) {
        try {
            return (ModelAndView) method.invoke(Controllers.REQUEST_MAPPING_CONTROLLERS.get(method), httpRequest, httpResponse);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error(e.getMessage());
            throw new NotFoundUrlException(e);
        }
    }
}
