package webserver;

import controller.Controller;
import controller.ControllerHandler;
import http.request.HttpRequest;
import http.request.RequestHandler;
import http.response.HttpResponse;
import http.response.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpProcess implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(HttpProcess.class);

    private Socket connection;

    public HttpProcess(Socket connection) {
        this.connection = connection;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        HttpRequest httpRequest = null;
        HttpResponse httpResponse = null;

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            RequestHandler requestHandler = new RequestHandler(new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));
            httpRequest = requestHandler.create();
            logger.debug("request path : {}", httpRequest.getPath());

            httpResponse = new ResponseHandler().create();
            httpResponse.addHeaderFromRequest(httpRequest);

            Controller controller = ControllerHandler.findByPath(httpRequest.getPath());
            controller.service(httpRequest, httpResponse);

            DataOutputStream dos = new DataOutputStream(out);
            dos.writeBytes(httpResponse.toString());
            httpResponse.write(dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
