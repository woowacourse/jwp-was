package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.message.exception.UrlDecodeException;

import java.io.DataOutputStream;
import java.io.IOException;
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
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final byte[] res = RequestDispatcher.forward(new IOUtils(in));
            final DataOutputStream dos = new DataOutputStream(out);
            writeResponse(dos, res);
        } catch (IOException | NullPointerException | UrlDecodeException e) {
            logger.error("run() {}", e.getMessage());
        }
    }

    private void writeResponse(final DataOutputStream dos, final byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error("writeResponse() {}", e.getMessage());

        }
    }
}
