package webserver;

import http.HttpRequest;
import http.HttpRequestParser;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private DispatcherServlet dispatcherServlet;
    private SessionInitiator sessionInitiator;
    private Socket connection;

    public RequestHandler(Socket connectionSocket, DispatcherServlet dispatcherServlet, SessionInitiator sessionInitiator) {
        this.connection = connectionSocket;
        this.dispatcherServlet = dispatcherServlet;
        this.sessionInitiator = sessionInitiator;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequestParser.parse(in);
            HttpResponse httpResponse = new HttpResponse();
            DataOutputStream dos = new DataOutputStream(out);

            sessionInitiator.handle(httpRequest, httpResponse);
            dispatcherServlet.doDispatch(httpRequest, httpResponse);
            httpResponse.send(dos);
            dos.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}