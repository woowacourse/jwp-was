package webserver;

import http.RequestBody;
import http.RequestHeader;
import http.RequestLine;
import http.ResponseHeader;
import http.controller.Controller;
import http.controller.IndexController;
import http.controller.RawFileController;
import http.controller.UserCreateController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            DataOutputStream dos = new DataOutputStream(out);
            route(br, dos);
        } catch (IOException | NullPointerException e) {
            logger.error(e.getMessage());
        }
    }

    private void route(BufferedReader br, DataOutputStream dos) throws IOException {
        Map<String, Controller> controllers = new HashMap<>();
        controllers.put("/user/create", new UserCreateController());
        controllers.put("/", new IndexController());
        try {
            RequestLine requestLine = new RequestLine(br);
            RequestHeader requestHeader = new RequestHeader(br);

            Controller controller = controllers.getOrDefault(requestLine.getPath(), new RawFileController(requestLine.getPath()));

            if (requestLine.isMethodEqualTo("GET")) {
                controller.get(dos, requestHeader);
            } else if (requestLine.isMethodEqualTo("POST")) {
                RequestBody requestBody = new RequestBody(br, requestHeader.getContentLength());
                controller.post(dos, requestHeader, requestBody);
            } else {
                throw new IllegalArgumentException("Unsupported method: PUT, DELETE");
            }
        } catch (IllegalArgumentException e) {
            ResponseHeader.response400Header(dos);
        }
    }
}
