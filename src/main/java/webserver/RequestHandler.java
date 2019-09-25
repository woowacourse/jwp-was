package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.io.NetworkBlockingIO;
import utils.io.NetworkIO;
import webserver.router.Router;

import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static Router router;

    private final Socket connection;

    public static void setRouter(Router _router) {
        router = _router;
    }

    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    public void run() {
        logger.debug(
                "New Client Connect! Connected IP : {}, Port : {}",
                this.connection.getInetAddress(),
                this.connection.getPort()
        );

        NetworkBlockingIO.init(this.connection).ifPresent(io -> {
            sendResponse(io, HttpRequest.deserialize(io).map(router::serve)
                                                        .orElse(HttpResponse.BAD_REQUEST));
            io.close();
        });
    }

    public void sendResponse(NetworkIO io, HttpResponse res) {
        io.write(res.serialize());
        logger.debug("Response:\r\n" + res.serializeHeader());
    }
}