package webserver;

import http.HttpRequest;
import http.HttpResponse;
import http.RequestMethod;
import http.controller.Controller;
import http.controller.Controllers;
import http.controller.ControllersFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpResponseHeaderParser;
import utils.HttpResponseUtils;

import java.io.*;
import java.net.Socket;

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
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void route(BufferedReader br, DataOutputStream dos) throws IOException {
        Controllers controllers = ControllersFactory.getControllers();
        try {
            HttpRequest httpRequest = new HttpRequest(br);
            Controller controller = controllers.find(httpRequest.getPath());
            RequestMethod requestMethod = httpRequest.getRequestMethod();
            HttpResponse httpResponse = requestMethod.extractResponse(controller, httpRequest);
            HttpResponseUtils.response(dos, httpResponse);
        } catch (IllegalArgumentException e) {
            HttpResponseUtils.response(dos, new HttpResponse(HttpResponseHeaderParser.badRequest()));
        }
    }
}
