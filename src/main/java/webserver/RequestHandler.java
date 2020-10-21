package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.HandlerMapping;
import web.HttpRequest;
import web.HttpResponse;
import web.servlet.Controller;

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

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            HttpRequest httpRequest = new HttpRequest(br);
            HttpResponse httpResponse = new HttpResponse(dos);

            Controller controller = HandlerMapping.find(httpRequest);
            controller.doService(httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
