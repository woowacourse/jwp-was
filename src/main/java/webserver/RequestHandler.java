package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.io.NetworkIO;
import utils.io.NetworkIOStream;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.net.Socket;

public class RequestHandler implements Runnable {
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

        NetworkIOStream.init(this.connection).ifPresent(io -> {
            sendResponse(io, HttpRequest.deserialize(io).map(Router::serve).orElse(HttpResponse.BAD_REQUEST));
            io.close();
        });
    }

    public void sendResponse(NetworkIO io, HttpResponse res) {
        io.write(res.serialize());
        logger.debug("\r\n" + res.serializeHeader());
    }
}