package webserver.handler;

import static utils.IOUtils.writeWithLineSeparator;
import static webserver.handler.Controller.mapping;
import static webserver.response.Response.emptyResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.Status;
import webserver.request.Request;
import webserver.request.RequestType;
import webserver.response.Response;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Request request = requestParser(in);
            RequestType requestType = RequestType.of(request);

            Response response = mapping(requestType).apply(request, emptyResponse());
            DataOutputStream dos = new DataOutputStream(out);
            response(response, dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private Request requestParser(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line;
        List<String> lines = new ArrayList<>();

        while (!"".equals(line = br.readLine()) && line != null) {
            logger.debug(line);
            lines.add(line);
        }

        return new Request(lines, br);
    }

    private void response(Response response, DataOutputStream dos) {
        try {
            responseFirstLine(response, dos);
            responseHeaders(dos, response);
            dos.writeBytes(System.lineSeparator());
            responseBody(response, dos);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseFirstLine(Response response, DataOutputStream dos) throws IOException {
        Status status = response.getStatus();
        writeWithLineSeparator(dos,
            String.format("%s %d %s", response.getVersion(), status.getCode(), status.getMessage()));
    }

    private void responseHeaders(DataOutputStream dos, Response response) throws IOException {
        Map<String, String> headers = response.getHeaders();
        for (String name : headers.keySet()) {
            writeWithLineSeparator(dos, String.format("%s %s", name, headers.get(name)));
        }
    }

    private void responseBody(Response response, DataOutputStream dos) throws IOException {
        if (!response.isBodyEmpty()) {
            byte[] body = response.getBody();
            dos.write(body, 0, body.length);
        }
    }
}
