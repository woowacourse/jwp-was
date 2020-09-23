package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.FileIoUtils;
import utils.IOUtils;
import utils.ModelType;
import utils.RequestUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    public static final String TEMPLATE_PATH = "./templates";
    public static final String STATIC_PATH = "./static";
    public static final String HTML = "html";

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequest httpRequest = new HttpRequest(br);
            printHeader(httpRequest);
            createModel(br, httpRequest);
            byte[] body = loadStaticFile(httpRequest);
            response(out, httpRequest.getPath(), body.length);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void printHeader(HttpRequest httpRequest) {
        logger.debug("header : {}", httpRequest.getHttpMethod() + " " + httpRequest.getPath());
        httpRequest.getHeader().forEach((key, value) -> logger.debug("header : {}", key + " " + value));
    }

    private byte[] loadStaticFile(HttpRequest httpRequest) throws IOException, URISyntaxException {
        String path = httpRequest.getPath();
        if (HTML.equals(RequestUtils.extractExtension(path))) {
            return FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + path);
        }
        return FileIoUtils.loadFileFromClasspath(STATIC_PATH + path);
    }

    private void createModel(BufferedReader br, HttpRequest httpRequest) throws IOException {
        if (HttpMethod.POST == httpRequest.getHttpMethod()) {
            ModelType modelType = ModelType.valueOf(RequestUtils.extractTitleOfModel(httpRequest.getPath()));
            String body = IOUtils.readData(br, Integer.parseInt(httpRequest.getHeader("Content-Length")));
            logger.debug(modelType.getModel(body).toString());
        }
    }

    private void response(OutputStream out, String path, int lengthOfBodyContent) throws
        IOException,
        URISyntaxException {
        HttpResponse httpResponse;
        if (lengthOfBodyContent != 0) {
            httpResponse = new HttpResponse(HttpStatus.OK, out);
            httpResponse.addHeader("Content-Type",
                String.format("text/%s;charset=utf-8", RequestUtils.extractExtension(path)));
            httpResponse.addHeader("Content-Length", RequestUtils.extractExtension(path));
            httpResponse.forward(path);
        }
        if (lengthOfBodyContent == 0) {
            httpResponse = new HttpResponse(HttpStatus.FOUND, out);
            httpResponse.addHeader("Content-Length", RequestUtils.extractExtension(path));
            httpResponse.sendRedirect("/index.html");
        }
    }
}
