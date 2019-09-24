package webserver;

import controller.Controller;
import controller.Controllers;
import controller.exception.NotFoundUrlException;
import model.http.HttpRequest;
import model.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.RequestHeaderParser;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String STATIC_PATH = "static";
    private static final String TEMPLATE_PATH = "templates";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = RequestHeaderParser.parseRequest(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpResponse httpResponse = HttpResponse.of();

            Controller controller = findController(httpRequest);
            controller.service(httpRequest, httpResponse);

            httpResponse = checkResponse(httpResponse);
            handleOutputStream(out, httpResponse);

        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
            throw new NotFoundUrlException(e);
        }
    }

    private Controller findController(HttpRequest httpRequest) {
        return Controllers.LIST.stream()
                .filter(target -> target.isMapping(httpRequest))
                .findFirst()
                .orElseThrow(NotFoundUrlException::new);
    }

    private HttpResponse checkResponse(HttpResponse httpResponse) {
        if (httpResponse.isNotInitialized()) {
            httpResponse = HttpResponse.createErrorResponse();
        }
        return httpResponse;
    }

    private void handleOutputStream(OutputStream out, HttpResponse httpResponse) throws IOException, URISyntaxException {
        DataOutputStream dos = new DataOutputStream(out);
        if (httpResponse.isNotInitialized() || httpResponse.hasError()) {
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates/error.html");
            createResponse(httpResponse, dos, body);
            return;
        }

        byte[] body = FileIoUtils.loadFileFromClasspath(httpResponse.getPath());
        createResponse(httpResponse, dos, body);
    }

    private void createResponse(HttpResponse httpResponse, DataOutputStream dos, byte[] body) {
        responseHeader(dos, body.length, httpResponse);
        responseBody(dos, body);
    }

    private void responseHeader(DataOutputStream dos, int lengthOfBodyContent, HttpResponse response) {
        try {
            dos.writeBytes("HTTP/1.1 " + response.getHttpStatusCode() + " " + response.getHttpReasonPhrase() + " \r\n");
            if (response.isRedirect()) {
                dos.writeBytes("Location: " + response.getResourceName() + " \r\n");
            }
            dos.writeBytes("Content-Type: " + response.getMediaType() + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
