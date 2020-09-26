package webserver;

import http.HttpRequest;
import http.HttpResponse;
import http.RequestMethod;
import http.controller.Controller;
import http.controller.IndexController;
import http.controller.RawFileController;
import http.controller.UserCreateController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpResponseHeaderParser;
import utils.HttpResponseUtils;

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
            HttpRequest httpRequest = new HttpRequest(br);
            HttpResponse httpResponse;
            Controller controller = controllers.getOrDefault(httpRequest.getPath(), new RawFileController(httpRequest.getPath()));

            if (httpRequest.isMethodEqualTo(RequestMethod.GET)) {
                httpResponse = controller.get(httpRequest);
            } else if (httpRequest.isMethodEqualTo(RequestMethod.POST)) {
                httpResponse = controller.post(httpRequest);
            } else {
                throw new IllegalArgumentException("Unsupported method: PUT, DELETE");
            }
            HttpResponseUtils.response(dos, httpResponse);
        } catch (IllegalArgumentException e) {
            HttpResponseUtils.response(dos, new HttpResponse(HttpResponseHeaderParser.response400Header()));
        }
    }
}
