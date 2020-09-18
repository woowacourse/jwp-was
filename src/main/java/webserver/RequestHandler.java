package webserver;

import controller.Controller;
import controller.ControllerMapper;
import controller.StaticFileController;
import controller.UserController;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private ControllerMapper controllerMapper = new ControllerMapper();
    private UserController userController = new UserController();
    private StaticFileController staticFileController = new StaticFileController();

    RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(), connection.getPort());

        try (
            InputStream in = connection.getInputStream();
            OutputStream out = connection.getOutputStream()
        ) {
            HttpRequest httpRequest = HttpRequest.readHttpRequest(in);
            logger.debug("Receive HttpRequest\n{}", httpRequest.toString());

            Controller controller = controllerMapper.findController(httpRequest);
            HttpResponse response = controller.service(httpRequest);

            DataOutputStream dos = new DataOutputStream(out);

            try {
                dos.writeBytes(response.buildHeader());

                byte[] body = response.getBody();
                dos.write(body, 0, body.length);
                dos.flush();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
