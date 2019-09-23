package webserver;

import controller.Controller;
import controller.ControllerFactory;
import http.request.*;
import http.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
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

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            RequestInformation requestInformation = HttpParser.parse(br);
            //우선 Controller를 찾음 ==> 없으면 FileController
            //FileController에 없으면 에러 잡아서

            RequestMethod method = requestInformation.extractMethod();
            RequestUrl url = requestInformation.extractUrl();
            log.error("this is the url ==> {}", url.getDestinationFolderUrlPath());
            Request request = new Request(method, url, requestInformation);

            ControllerFactory factory = new ControllerFactory();
            Controller controller = factory.createController(request);
            Response response = controller.createResponse(request);

            DataOutputStream dos = new DataOutputStream(out);
            response.doResponse(dos);
        } catch (IOException | URISyntaxException e) {
            log.error(e.getMessage());
        }
    }
}
