package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String CONTENT_TYPE_HEADER_KEY = "Content-type";
    private static final String NEXT_LINE = "\r\n";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            HttpResponse res = RequestDispatcher.handle(RequestParser.parse(in));
            writeResponse(dos, res);
        } catch (IOException e) {
            logger.error("Error: ", e);
        }
    }

    private void writeResponse(DataOutputStream dos, HttpResponse response) {
        try {
            writeHeader(dos, response);
            dos.writeBytes(NEXT_LINE); // Boundary between http headers and message body
            writeBody(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeHeader(DataOutputStream dos, HttpResponse response) throws IOException {
        dos.writeBytes(String.format("%s %d %s%s", HTTP_VERSION, response.getStatus().getCode(), response.getStatus().getText(), NEXT_LINE));
        writeContentType(dos, response);
        response.getHeaderKeys()
            .forEach(k -> writeHeaderLine(dos, k + ": " + response.getHeader(k)));
    }

    private void writeContentType(DataOutputStream dos, HttpResponse response) {
        if (response.getMediaType() != null) {
            writeHeaderLine(dos, String.format("%s: %s", CONTENT_TYPE_HEADER_KEY, response.getMediaType()));
        }
    }

    private void writeHeaderLine(DataOutputStream dos, String header) {
        try {
            dos.writeBytes(header + NEXT_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeBody(DataOutputStream dos, HttpResponse response) {
        try {
            writeBodyIfExist(dos, response);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeBodyIfExist(DataOutputStream dos, HttpResponse response) throws IOException {
        if (response.getBody() != null) {
            dos.write(response.getBody(), 0, response.getBody().length);
        }
    }
}
