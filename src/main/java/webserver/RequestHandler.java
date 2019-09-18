package webserver;

import http.HttpPath;
import http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.NetworkIOStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug(
                "New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort()
        );

        NetworkIOStream.init(this.connection).ifPresent(io -> {
            HttpRequest.parse(io).ifPresent(req ->
                FileIoUtils.loadFileFromClasspath(route(req.path())).ifPresent(body -> {
                    response200Header(io.getDos(), body.length());
                    responseBody(io.getDos(), body);
                })
            );
            io.close();
        });
    }

    private String route(HttpPath path) {
        switch(path.extension()) {
            case "htm":
            case "html":
            case "ico":
                return "./templates" + path.get();
            default:
                return "./static" + path.get();
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, String body) {
        try {
            dos.write(body.getBytes());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
