import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.Controller;
import domain.request.HttpServletRequest;
import domain.response.HttpServletResponse;
import servlet.HttpRequest;
import servlet.HttpResponse;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Controller frontController;

    private Socket connection;

    public RequestHandler(Socket connectionSocket, Controller frontController) {
        this.connection = connectionSocket;
        this.frontController = frontController;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream();
             BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(in, StandardCharsets.UTF_8)));
             DataOutputStream dataOutputStream = new DataOutputStream(out)) {

            HttpRequest httpRequest = new HttpServletRequest(bufferedReader);
            HttpResponse httpResponse = new HttpServletResponse(dataOutputStream);

            frontController.service(httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
