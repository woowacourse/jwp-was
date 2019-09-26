package webserver;

import http.controller.HttpRequestHandlers;
import http.model.HttpProtocols;
import http.model.HttpRequest;
import http.model.HttpResponse;
import http.model.HttpStatus;
import http.supoort.HttpRequestParser;
import http.supoort.ResponseMessageConverter;
import http.view.ModelAndView;
import http.view.ViewHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private HttpRequestHandlers httpRequestHandlers;
    private ViewHandler viewHandler;

    public RequestHandler(Socket connectionSocket, HttpRequestHandlers httpRequestHandlers, ViewHandler viewHandler) {
        this.connection = connectionSocket;
        this.httpRequestHandlers = httpRequestHandlers;
        this.viewHandler = viewHandler;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            handleRequest(in, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void handleRequest(InputStream in, OutputStream out) {
        try {
            HttpRequest httpRequest;
            HttpResponse httpResponse;
            httpRequest = HttpRequestParser.parse(in);

            ModelAndView modelAndView = httpRequestHandlers.doService(httpRequest);

            response(viewHandler.handle(modelAndView), out);

        } catch (Exception e) {
            logger.error(e.getMessage());
            sendError(e.getMessage(), out);
        }
    }

    private void sendError(String message, OutputStream out) {
        DataOutputStream dos = new DataOutputStream(out);
        try {
            dos.writeBytes(HttpProtocols.HTTP1_1 + " " + HttpStatus.BAD_REQUEST + "\r\n");
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void response(HttpResponse response, OutputStream out) {
        DataOutputStream dos = new DataOutputStream(out);
        ResponseMessageConverter.convert(response, dos);
    }


}
