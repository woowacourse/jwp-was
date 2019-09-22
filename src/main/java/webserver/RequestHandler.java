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

    private Socket connection;
    private UrlMapper urlMapper;

    public RequestHandler(Socket connection, UrlMapper urlMapper) {
        this.connection = connection;
        this.urlMapper = urlMapper;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest httpRequest = HttpRequestFactory.getInstance().getHttpRequest(in);

            String view = urlMapper.service(httpRequest);

            ViewProcessor viewProcessor = ViewProcessorFactory.getInstance().getViewProcessor(view);
            viewProcessor.process(dos, view);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
