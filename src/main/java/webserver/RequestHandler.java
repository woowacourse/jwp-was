package webserver;

import controller.Controller;
import controller.ModelAndView;
import controller.exception.MethodNotAllowedException;
import fileloader.ResourceFileLoader;
import http.request.HttpRequest;
import http.request.HttpRequestCreator;
import http.request.exception.InvalidHttpRequestException;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import http.response.exception.HttpVersionNotSupportedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.View;
import view.ViewResolver;
import webserver.exception.ResourceNotFoundException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private static final String STATIC_RESOURCE_PATH_PREFIX = "./static";
    private final ViewResolver viewResolver = ViewResolver.getInstance();
    private final ResourceHttpRequestHandler resourceHttpRequestHandler =
            new ResourceHttpRequestHandler(new ResourceFileLoader(STATIC_RESOURCE_PATH_PREFIX));
    private final HandlerMapping handlerMapping = HandlerMapping.getInstance();
    private Socket connection;

    RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        //TODO : IOException 처리
        try (InputStream inputStream = connection.getInputStream(); OutputStream outputStream = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequestCreator.create(inputStream);
            HttpResponse httpResponse = new HttpResponse(httpRequest);

            handleRequest(httpRequest, httpResponse);

            sendResponse(outputStream, httpResponse);
        } catch (IOException | InvalidHttpRequestException e) {
            log.error(e.getMessage(), e.getCause());
        }
    }

    private void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            checkHttpVersion(httpRequest);
            if (!resourceHttpRequestHandler.handleHttpRequest(httpRequest, httpResponse)) {
                Controller controller = handlerMapping.getHandler(httpRequest.getPath());
                ModelAndView modelAndView = controller.service(httpRequest, httpResponse);
                View view = viewResolver.resolve(modelAndView.getViewName());
                view.render(modelAndView.getModelMap(), httpResponse);
            }
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e.getCause());
            httpResponse.setResponseStatus(ResponseStatus.NOT_FOUND);
        } catch (MethodNotAllowedException e) {
            log.error(e.getMessage(), e.getCause());
            httpResponse.setResponseStatus(ResponseStatus.METHOD_NOT_ALLOWED);
        } catch (HttpVersionNotSupportedException e) {
            log.error(e.getMessage(), e.getCause());
            httpResponse.setResponseStatus(ResponseStatus.HTTP_VERSION_NOT_SUPPORTED);
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e.getCause());
            httpResponse.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    private void checkHttpVersion(HttpRequest httpRequest) {
        if (httpRequest.getHttpVersion().isNotSupportedVersion()) {
            throw new HttpVersionNotSupportedException();
        }
    }

    private void sendResponse(OutputStream out, HttpResponse httpResponse) {
        DataOutputStream dos = new DataOutputStream(out);

        try {
            byte[] response = httpResponse.serialize();
            dos.write(response, 0, response.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
        }
    }
}
