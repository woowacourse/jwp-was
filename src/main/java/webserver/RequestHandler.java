package webserver;

import controller.Controller;
import controller.ControllerContainer;
import http.request.HttpRequest;
import http.request.factory.HttpRequestFactory;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.RequestParser;
import view.ModelAndView;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = connection.getOutputStream()) {

            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            HttpRequest httpRequest = HttpRequestFactory.create(RequestParser.lineParse(inputStream));
            HttpResponse httpResponse = HttpResponse.of(httpRequest);

            if (httpRequest.isContainExtension()) {
                httpResponse.forward(httpRequest.getUri());
                httpResponse.writeResponse(dataOutputStream);
                return;
            }

            Controller controller = ControllerContainer.getController(httpRequest.isContainExtension(), httpRequest.getUri());
            Object object = controller.service(httpRequest, httpResponse);

            if (object instanceof ModelAndView) {
                ((ModelAndView) object).render(httpRequest, httpResponse);
            }

            httpResponse.writeResponse(dataOutputStream);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
