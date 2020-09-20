package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final byte[] DEFAULT_BODY = "Hello World".getBytes();
    private static final byte[] ERROR_BODY = "Error".getBytes();

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            HttpResponse httpResponse = handle(new HttpRequest(in));
            readResponse(httpResponse, dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void readResponse(HttpResponse httpResponse, DataOutputStream dos) {
        try {
            dos.writeBytes(httpResponse.getHttpResponseHeader().format());
            dos.write(httpResponse.getBody(), 0, httpResponse.getBody().length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public HttpResponse handle(HttpRequest httpRequest) {
        return RequestProcessor.process(httpRequest);
    }
}