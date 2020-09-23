package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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
    public static final int OFFSET = 0;
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
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = loadStaticFile(httpRequest);
            responseHeader(dos, httpRequest.getPath(), body.length);
            responseBody(dos, body);
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

    private void responseHeader(DataOutputStream dos, String path, int lengthOfBodyContent) {
        if (lengthOfBodyContent != 0) {
            response200Header(dos, path, lengthOfBodyContent);
        }
        if (lengthOfBodyContent == 0) {
            response302Header(dos, lengthOfBodyContent);
        }
    }

    private void response200Header(DataOutputStream dos, String path, int lengthOfBodyContent) {
        try {
            writeWithLineSeparator(dos, "HTTP/1.1 200 OK");
            writeWithLineSeparator(dos,
                String.format("Content-Type: text/%s;charset=utf-8", RequestUtils.extractExtension(path)));
            writeWithLineSeparator(dos, String.format("Content-Length: %d", lengthOfBodyContent));
            dos.writeBytes(System.lineSeparator());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            writeWithLineSeparator(dos, "HTTP/1.1 302 FOUND");
            writeWithLineSeparator(dos, "Location: http://localhost:8080/index.html");
            writeWithLineSeparator(dos, "Content-Type: text/html;charset=utf-8");
            writeWithLineSeparator(dos, String.format("Content-Length: %d", lengthOfBodyContent));
            dos.writeBytes(System.lineSeparator());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeWithLineSeparator(DataOutputStream dos, String contents) throws IOException {
        dos.writeBytes(String.format("%s%s", contents, System.lineSeparator()));
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, OFFSET, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
