package webserver;

import controller.Controller;
import controller.ControllerFactory;
import http.request.HttpRequestFactory;
import http.request.Request;
import http.response.Response;
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
            String firstLine = br.readLine();
            log.debug("requestResolver : {}", firstLine);
            List<String> lines = parsedBufferedReader(br);
            Request request = HttpRequestFactory.getRequest(firstLine, lines, br);

            Controller controller = ControllerFactory.getController(request);
            Response response = controller.createResponse();

            DataOutputStream dos = new DataOutputStream(out);
            response.doResponse(dos);

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
