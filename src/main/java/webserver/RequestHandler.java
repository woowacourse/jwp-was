package webserver;

import controller.Controller;
import controller.controllermapper.ControllerFactory;
import http.request.Request;
import http.request.RequestInformation;
import http.request.RequestMethod;
import http.request.RequestUrl;
import http.request.parser.HttpParser;
import http.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;

    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream();
             DataOutputStream dos = new DataOutputStream(out)) {

            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            RequestInformation requestInformation = HttpParser.parse(br);

            RequestMethod method = requestInformation.extractMethod();
            RequestUrl url = requestInformation.extractUrl();
            log.debug("this is the url ==> {}", url.getDestinationFolderUrlPath());
            Request request = new Request(method, url, requestInformation);
            Response response = new Response();

            ControllerFactory factory = new ControllerFactory();
            Controller controller = factory.mappingController(request);
            controller.processResponse(request, response);

            dos.write(response.createBytes());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
