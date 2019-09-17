package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            Response res = RequestController.handle(RequestParser.parse(in));
            DataOutputStream dos = new DataOutputStream(out);
            writeResponse(dos, res);
        } catch (IOException e) {
            logger.error("Error: ", e);
        }
    }

    private void writeResponse(DataOutputStream dos, Response response) {
        try {
            writeHeader(dos, response);
            responseBody(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeHeader(DataOutputStream dos, Response response) throws IOException {
        dos.writeBytes(String.format("HTTP/1.1 %d %s", response.getStatusCode(), response.getStatusText()));
        response.getHeaderKeys()
            .forEach(k -> writeHeaderLine(dos, response.getHeader(k)));
        dos.writeBytes("\r\n");
    }

    private void writeHeaderLine(DataOutputStream dos, String header) {
        try {
            dos.writeBytes(header + "\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, Response response) {
        try {
            dos.write(response.getBody(), 0, response.getBody().length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
