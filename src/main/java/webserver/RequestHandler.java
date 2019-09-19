package webserver;

import webserver.http.HttpPath;
import webserver.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.io.FileIoUtils;
import utils.io.NetworkIO;
import utils.io.NetworkIOStream;
import utils.parser.KeyValueParserFactory;

import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final KeyValueParserFactory keyValueParserFactory = KeyValueParserFactory.getInstance();

    private final Socket connection;

    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    public void run() {
        logger.debug(
                "New Client Connect! Connected IP : {}, Port : {}",
                this.connection.getInetAddress(),
                this.connection.getPort()
        );

        try (final NetworkIO io = new NetworkIOStream(this.connection)) {
            HttpRequest.deserialize(io, keyValueParserFactory).ifPresent(req ->
                FileIoUtils.loadFileFromClasspath(route(req.path())).ifPresent(body -> {
                    response200Header(io, req.path(), body.length);
                    responseBody(io, body);
                })
            );
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private String route(HttpPath path) {
        switch(path.extension()) {
            case "htm":
            case "html":
                return "./templates" + path.get();
            default:
                return "./static" + path.get();
        }
    }

    private void response200Header(NetworkIO io, HttpPath path, int lengthOfBodyContent) {
        String contentType = "text/html";
        if (path.extension().equals("css")) {
            contentType = "text/css";
        }
        io.write(
                "HTTP/1.1 200 OK \r\n"
                + "Content-Type: " + contentType + ";charset=utf-8\r\n"
                + "Content-Length: " + lengthOfBodyContent + "\r\n"
                + "\r\n"
        );
    }

    private void responseBody(NetworkIO io, byte[] body) {
        io.write(body);
    }
}