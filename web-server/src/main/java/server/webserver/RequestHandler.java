package server.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.web.controller.HandlerMapping;
import server.web.HttpHeader;
import server.web.controller.Controller;
import server.web.request.HttpRequest;
import server.web.response.HttpResponse;
import server.web.view.ModelAndView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final HandlerMapping handlerMapping;

    public RequestHandler(Socket connection, HandlerMapping handlerMapping) {
        this.connection = connection;
        this.handlerMapping = handlerMapping;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            HttpRequest httpRequest = new HttpRequest(br);
            HttpResponse httpResponse = new HttpResponse(dos, HttpHeader.ofResponse());
            httpResponse.addSession(httpRequest.getSessionId());

            Controller controller = handlerMapping.find(httpRequest);

            ModelAndView modelAndView = controller.doService(httpRequest, httpResponse);
            modelAndView.render(httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
