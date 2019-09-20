package webserver;

import http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.controller.Controller;
import webserver.controller.CreateUserController;
import webserver.controller.FileController;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static Map<String, Controller> api;

    static {
        api = new HashMap<>();
        api.put("/user/create", new CreateUserController());
    }

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Request request = readRequestUrl(in);
            Response response = new Response(new ResponseHeader());

            Controller controller = Optional.ofNullable(api.get(request.extractUrl())).orElseGet(FileController::new);
            controller.service(request, response);

            response.writeMessage(new DataOutputStream(out));
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private Request readRequestUrl(InputStream in) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(inputStreamReader);
        RequestHeader header = new RequestHeader(IOUtils.parseData(br));

        if (header.get(RequestHeader.HTTP_METHOD).equals("POST")) {
            String body = IOUtils.readData(br, Integer.parseInt(header.get("content-length")));
            RequestBody requestBody = new RequestBody(body);
            return new Request(header, requestBody);
        }
        return new Request(header);
    }
}
