package webserver;

import controller.Controller;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Objects;
import java.util.UUID;
import model.general.Header;
import model.general.Headers;
import model.general.Status;
import model.request.HttpRequest;
import model.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ControllerMapper;

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

            HttpResponse httpResponse = makeResponse(inputStream);
            httpResponse.writeToOutputStream(dataOutputStream);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private HttpResponse makeResponse(InputStream inputStream) {
        HttpRequest httpRequest;

        try {
            httpRequest = HttpRequest.of(inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return HttpResponse.of(Status.BAD_REQUEST);
        }

        Controller controller = ControllerMapper.selectController(httpRequest);
        if (Objects.isNull(controller)) {
            return HttpResponse.of(Status.NOT_FOUND);
        }

        try {
            HttpResponse httpResponse = controller.service(httpRequest);
            makeSession(httpRequest, httpResponse);
            return httpResponse;
        } catch (Exception e) {
            return HttpResponse.of(Status.INTERNAL_ERROR);
        }
    }

    private void makeSession(HttpRequest httpRequest, HttpResponse httpResponse) {
        if(!httpRequest.hasSession()){
            Headers headers = httpResponse.getHeaders();
            UUID uuid = UUID.randomUUID();

            headers.addHeader(Header.SET_COOKIE, "JSESSIONID=" + uuid.toString());
        }
    }
}
