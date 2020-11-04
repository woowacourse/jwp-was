import controller.Controller;
import http.message.HttpRequestMessage;
import http.message.HttpResponseMessage;
import http.request.HttpUri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                     connection.getPort());

        try (InputStream in = connection.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
             ResponseWriter responseWriter = new ResponseWriter(this.connection)) {

            HttpRequestMessage httpRequestMessage = HttpRequestMessage.from(br);

            HttpUri httpUri = httpRequestMessage.getHttpUri();
            Controller controller = httpUri.findController();

            HttpResponseMessage httpResponseMessage = controller.createHttpResponseMessage(httpRequestMessage);

            responseWriter.write(httpResponseMessage);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
