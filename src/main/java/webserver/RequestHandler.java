package webserver;

import controller.Controller;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Objects;
import model.general.Status;
import model.request.HttpRequest;
import model.response.HttpResponse;
import model.response.StatusLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger
            .debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream inputStream = connection.getInputStream();
            OutputStream outputStream = connection.getOutputStream()) {
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            HttpResponse response = makeResponse(inputStream);
            response.writeToOutputStream(dataOutputStream);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private HttpResponse makeResponse(InputStream inputStream) {
        HttpRequest request;
        try {
            request = HttpRequest.of(inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return HttpResponse.of(Status.BAD_REQUEST);
        }

        Controller controller = ControllerMapper.selectController(request);
        if(Objects.isNull(controller)){
            return HttpResponse.of(Status.NOT_FOUND);
        }
        return controller.service(request);
    }
}
