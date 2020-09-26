package webserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Model;
import utils.FileIoUtils;
import utils.RequestUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    public static final String TEMPLATE_CLASS_PATH = "./templates";
    public static final String STATIC_CLASS_PATH = "./static";
    public static final String TEMPLATE_PATH = "./src/main/resources/templates";
    public static final String STATIC_PATH = "./src/main/resources/static";

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
            createModel(httpRequest);
            byte[] body = loadStaticFile(httpRequest);
            response(out, httpRequest.getPath(), body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void printHeader(HttpRequest httpRequest) {
        logger.debug("header : {}", httpRequest.getMethodName() + " " + httpRequest.getPath());
        httpRequest.getHeader()
            .forEach((key, value) -> logger.debug("header : {}", String.format("%s: %s", key, value)));
    }

    private byte[] loadStaticFile(HttpRequest httpRequest) throws IOException, URISyntaxException {
        String path = httpRequest.getPath();
        File file = new File(TEMPLATE_PATH + path);
        if (file.isFile()) {
            return FileIoUtils.loadFileFromClasspath(TEMPLATE_CLASS_PATH + path);
        }
        file = new File(STATIC_PATH + path);
        if (file.isFile()) {
            return FileIoUtils.loadFileFromClasspath(STATIC_CLASS_PATH + path);
        }
        return null;
    }

    private void createModel(HttpRequest httpRequest) {
        Model model = httpRequest.createModel();
        if (model != null) {
            printParameter(httpRequest);
            logger.debug("model : {}", model.toString());
        }
    }

    private void printParameter(HttpRequest httpRequest) {
        httpRequest.getParameter()
            .forEach((key, value) -> logger.debug("body : {}", String.format("%s = %s", key, value)));
    }

    private void response(OutputStream out, String path, byte[] body) throws
        IOException,
        URISyntaxException {
        HttpResponse httpResponse;
        if (body != null) {
            httpResponse = new HttpResponse(HttpStatus.OK, out);
            httpResponse.addHeader("Content-Type",
                String.format("text/%s;charset=utf-8", RequestUtils.extractExtension(path)));
            httpResponse.addHeader("Content-Length", String.valueOf(body.length));
            httpResponse.forward(path);
        }
        if (body == null) {
            httpResponse = new HttpResponse(HttpStatus.FOUND, out);
            httpResponse.sendRedirect("/index.html");
        }
    }
}
