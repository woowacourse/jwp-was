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
    public static final String EMPTY = "";
    public static final int OFFSET = 0;
    public static final String TEMPLATE_PATH = "./templates";
    public static final String STATIC_PATH = "./static";

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String url = readRequest(br);
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = loadFileIfStaticFileRequest(url);
            responseHeader(dos, url, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private String readRequest(BufferedReader br) throws IOException {
        int contentLength = 0;
        String line = br.readLine();
        String firstLine = line;
        String url = RequestUtils.extractPath(firstLine);
        while (!EMPTY.equals(line) && line != null) {
            logger.debug("header : {}", line);
            contentLength = assignContentLengthIfPresent(contentLength, line);
            line = br.readLine();
        }

        createModel(br, contentLength, firstLine);
        return url;
    }

    private byte[] loadFileIfStaticFileRequest(String url) throws IOException, URISyntaxException {
        if (!isApiCall(url)) {
            return loadStaticFile(url);
        }
        return new byte[0];
    }

    private byte[] loadStaticFile(String url) throws IOException, URISyntaxException {
        if ("html".equals(RequestUtils.extractExtension(url))) {
            return FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + url);
        }
        return FileIoUtils.loadFileFromClasspath(STATIC_PATH + url);
    }

    private void createModel(BufferedReader br, int contentLength, String firstLine) throws IOException {
        if (RequestUtils.extractMethod(firstLine).equals("POST")) {
            ModelType modelType = ModelType.valueOf(RequestUtils.extractTitleOfModel(firstLine));
            String body = IOUtils.readData(br, contentLength);
            logger.debug(modelType.getModel(body).toString());
        }
    }

    private int assignContentLengthIfPresent(int contentLength, String line) {
        if (line.startsWith("Content-Length")) {
            contentLength = Integer.parseInt(line.split(" ")[1]);
        }
        return contentLength;
    }

    private boolean isApiCall(String url) {
        return url.split("\\.").length == 1;
    }

    private void responseHeader(DataOutputStream dos, String url, int lengthOfBodyContent) {
        if (!isApiCall(url)) {
            response200Header(dos, url, lengthOfBodyContent);
        }
        if (isApiCall(url)) {
            response302Header(dos, lengthOfBodyContent);
        }
    }

    private void response200Header(DataOutputStream dos, String url, int lengthOfBodyContent) {
        try {
            writeWithLineSeparator(dos, "HTTP/1.1 200 OK");
            writeWithLineSeparator(dos,
                String.format("Content-Type: text/%s;charset=utf-8", RequestUtils.extractExtension(url)));
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
