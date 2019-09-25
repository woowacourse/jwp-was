package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.domain.Request;
import webserver.view.NetworkInput;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    RequestHandler(final Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        LOG.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (final InputStream in = connection.getInputStream(); final OutputStream out = connection.getOutputStream()) {
            final Request request = new Request(new NetworkInput(in));
            final byte[] response = processRequest(request);
            final DataOutputStream dos = new DataOutputStream(out);
            writeResponse(dos, response);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            LOG.error(e.getMessage());
        }
    }

    private byte[] processRequest(final Request request) {
        return RequestDispatcher.forward(request).toBytes();
    }

    private void writeResponse(final DataOutputStream dos, final byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }
}
