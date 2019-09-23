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
    private static final String SET_COOKIE_HEADER_KEY = "Set-Cookie";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            Request req = RequestParser.parse(in);
            Response res = RequestDispatcher.handle(req);
            writeResponse(dos, req, res);
        } catch (IOException e) {
            logger.error("Error: ", e);
        }
    }

    private void writeResponse(DataOutputStream dos, Request request, Response response) {
        try {
            writeHeader(dos, response);
            writeCookie(dos, request, response);
            dos.writeBytes(NEXT_LINE);
            writeBody(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeHeader(DataOutputStream dos, Response response) throws IOException {
        dos.writeBytes(String.format("%s %d %s%s", HTTP_VERSION, response.getStatus().getCode(), response.getStatus().getText(), NEXT_LINE));
        writeContentType(dos, response);
        response.getHeaderKeys()
                .forEach(k -> writeHeaderLine(dos, k + ": " + response.getHeader(k)));
    }

    private void writeContentType(DataOutputStream dos, Response response) {
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

    private void writeCookie(DataOutputStream dos, Request request, Response response) {
        response.getCookieKeys().forEach(key -> {
            logger.info("[cookie] {}={}", key, response.getCookie(key));
            writeHeaderLine(dos, String.format("%s: %s=%s; Path=/", SET_COOKIE_HEADER_KEY, key, response.getCookie(key)));
            writeHeaderLine(dos, String.format("%s: %s=%s; Path=/", SET_COOKIE_HEADER_KEY, Request.SESSION_COOKIE_KEY, request.getSession().getId()));
        });
    }

    private void writeBody(DataOutputStream dos, Response response) {
        try {
            writeBodyIfExist(dos, response);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeBodyIfExist(DataOutputStream dos, Response response) throws IOException {
        if (response.getBody() != null) {
            dos.write(response.getBody(), 0, response.getBody().length);
        }
    }
}
