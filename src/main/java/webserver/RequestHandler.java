package webserver;

import controller.Controller;
import controller.ControllerFactory;
import http.request.HttpRequestFactory;
import http.request.Request;
import http.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
            List<String> lines = parsedBufferedReader(br);
            Request request = HttpRequestFactory.getRequest(firstLine, lines);
            Map<String, String> parameters = new HashMap<>();

            parameters = checkGetOrPostParams(br, request, parameters);

            Controller controller = ControllerFactory.getController(request, parameters);
            Response response = controller.createResponse();

            DataOutputStream dos = new DataOutputStream(out);
            response.doResponse(dos);

        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> checkGetOrPostParams(BufferedReader br, Request request, Map<String, String> parameters) throws IOException {
        if (request.getRequestPath().getPath().contains("?")) {
            String[] params = request.getRequestPath().getPath().split("\\?");
            parameters = extractParameter(params[1].split("&"));
        }

        if (request.getRequestMethod().getMethod().equals("POST")) {
            String params = IOUtils.readData(br, Integer.parseInt(request.getRequestHeader().getHeaders().get("Content-Length")));
            parameters = extractParameter(params.split("&"));
        }
        return parameters;
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

    private Map<String, String> extractParameter(String[] params) {
        Map<String, String> parameters = new HashMap<>();
        Arrays.stream(params)
                .forEach(param -> {
                    String[] keyValues = param.split("=");
                    parameters.put(keyValues[0], keyValues[1]);
                });
        return parameters;
    }
}
