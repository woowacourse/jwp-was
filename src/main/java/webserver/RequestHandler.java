package webserver;

import http.HttpRequest;
import http.HttpRequestParser;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

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
            HttpRequest httpRequest = HttpRequestParser.parse(IOUtils.readInputStream(in));
            HttpResponse httpResponse = new HttpResponse();
            DataOutputStream dos = new DataOutputStream(out);

            DispatcherServlet.doDispatch(httpRequest, httpResponse);
            httpResponse.send(dos);
            dos.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}