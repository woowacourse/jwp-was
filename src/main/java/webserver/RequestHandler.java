package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.domain.Request;
import webserver.domain.Response;
import webserver.view.NetworkInput;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    RequestHandler(final Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        LOG.debug("New Client Connect! Connected IP: {}, Port: {}",
                connection.getInetAddress(),
                connection.getPort());

        try (final InputStream in = connection.getInputStream(); final OutputStream out = connection.getOutputStream()) {
            final Request request = new Request(new NetworkInput(in));
            final Response response = RequestDispatcher.forward(request);
            response.sendToClient(out);
        } catch (final Exception e) {
            LOG.error(e.getMessage());
        }
    }
}
