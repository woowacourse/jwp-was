package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.io.NetworkBlockingIO;
import utils.io.NetworkIO;
import webserver.env.Env;

import java.net.Socket;

public final class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

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

        NetworkBlockingIO.init(this.connection).ifPresent(io -> {
            sendResponse(io, HttpRequest.deserialize(io).map(req -> Env.router().serve(req))
                                                        .orElse(HttpResponse.BAD_REQUEST));
            io.close();
        });
    }

    public void sendResponse(NetworkIO io, HttpResponse res) {
        io.write(res.serialize());
        logger.debug("Response:\r\n{}", res.printHeader());
    }
}