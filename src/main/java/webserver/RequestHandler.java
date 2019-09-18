package webserver;

import http.controller.HttpRequestHandlers;
import http.model.HttpRequest;
import http.supoort.HttpRequestParser;
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
            try {
                HttpRequest request = HttpRequestParser.parse(in);

                ModelAndView modelAndView = httpRequestHandlers.doService(request);

                byte[] body = viewHandler.handle(modelAndView);

                DataOutputStream dos = new DataOutputStream(out);

                response200Header(dos, body.length);
                responseBody(dos, body);

            } catch (Exception e) {
                logger.error(e.getMessage());
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = "ERROR_".getBytes();

                response200Header(dos, body.length);
                responseBody(dos, body);
            }

        } catch (IOException e) {
            logger.error(e.getMessage());

        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
