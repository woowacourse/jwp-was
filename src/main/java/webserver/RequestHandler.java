package webserver;

import controller.Controller2;
import controller.Controller2Factory;
import http.request.*;
import http.response.Response2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
            
            RequestMethod method = requestInformation.extractMethod();
            RequestUrl url = requestInformation.extractUrl();
            log.error("this is the url ==> {}", url.getUrlPath());
            Request2 request = new Request2(method, url, requestInformation);

            Controller2Factory factory = new Controller2Factory();
            Controller2 controller = factory.createController(request);
            Response2 response = controller.createResponse(request);

            DataOutputStream dos = new DataOutputStream(out);
            response.doResponse(dos);

//            String firstLine = br.readLine();
//            log.debug("requestResolver : {}", firstLine);
//            List<String> lines = parsedBufferedReader(br);
//            ControllerMapper controllerMapper = HttpControllerMapperFactory.getControllerMapper(requestInformation);
//            Controller controller = ControllerFactory.getController(controllerMapper);
//            Response response = controller.createResponse();
//            DataOutputStream dos = new DataOutputStream(out);
//            response.doResponse(dos);
//            Request request = HttpRequestFactory.getRequest(firstLine, lines, br);
//            Controller controller = ControllerFactory.getController(request);
//            Response response = controller.createResponse();
//
//            DataOutputStream dos = new DataOutputStream(out);
//            response.doResponse(dos);
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (URISyntaxException e) {
          e.printStackTrace();
       }
    }

    private static List<String> parsedBufferedReader(BufferedReader br) throws IOException {
        List<String> requestLines = new ArrayList<>();
        String line = "Header: start";
        while (!line.equals("")) {
            requestLines.add(line);
            line = br.readLine();
        }
        return requestLines;
    }
}
