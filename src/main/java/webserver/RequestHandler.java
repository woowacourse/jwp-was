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

    private Socket connection;
    private static Map<String, Controller> api;

    static {
        api = new HashMap<>();
        api.put("/user/create", new CreateUserController());
    }

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = readRequestUrl(in);
            Response response = new Response(new ResponseHeader());

            Controller controller = Optional.ofNullable(api.get(httpRequest.getResourcePath()))
                    .orElseGet(FileController::new);
            controller.service(httpRequest, response);

            response.writeMessage(new DataOutputStream(out));
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpRequest readRequestUrl(InputStream in) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(inputStreamReader);
        HttpRequestHeader header = new HttpRequestHeader(IOUtils.parseHeader(br));

        // @TODO 메시지 보내듯이 수정
        if ("POST".equals(header.getMethod())) {
            String body = IOUtils.readData(br, Integer.parseInt(header.get("content-length")));
            HttpRequestBody httpRequestBody = new HttpRequestBody(body);
            return new HttpRequest(header, httpRequestBody);
        }
        return new HttpRequest(header);
    }
}
