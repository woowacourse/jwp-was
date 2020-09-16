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
    public static final String FILE_PATH = "./templates";

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
            byte[] body = {};
            if (!url.equals("/user/create")) {
                body = FileIoUtils.loadFileFromClasspath(FILE_PATH + url);
                response200Header(dos, body.length);
            }
            if (url.equals("/user/create")) {
                response302Header(dos, body.length);
            }
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private String readRequest(BufferedReader br) throws IOException {
        int contentLength = 0;
        String line = br.readLine();
        String firstLine = line;
        String url = RequestUtils.extractUrl(firstLine);
        while (!EMPTY.equals(line) && line != null) {
            logger.debug("header : {}", line);
            contentLength = assignContentLengthIfPresent(contentLength, line);
            line = br.readLine();
        }

        createModel(br, contentLength, firstLine);
        return url;
    }

    private void createModel(BufferedReader br, int contentLength, String firstLine) throws IOException {
        if (RequestUtils.isPost(firstLine)) {
            ModelType modelType = ModelType.valueOf(RequestUtils.extractTitleOfModel(firstLine).toUpperCase());
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

    private void response302Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: http://localhost:8080/index.html\r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
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
