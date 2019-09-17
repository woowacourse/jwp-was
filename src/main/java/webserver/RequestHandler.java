package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String NEW_LINE = "/n";
    private static final String SPACE = " ";
    private static final int FIRST_LINE_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final String TEMPLATES_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final String requestHeader = getRequestHeader(in);
            logger.info(requestHeader);
            final byte[] body = readBody(requestHeader);
            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            logger.error(e.getMessage());
        }
    }

    // TODO 메서드명 다시 고민하기
    private String getRequestHeader(final InputStream inputStream) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        final StringBuilder builder = new StringBuilder();
        while (true) {
            final String line = reader.readLine();
            if (Objects.isNull(line) || "".equals(line)) {
                break;
            }
            builder.append(line).append("\n");
        }
        return builder.toString();
    }

    private String extractRequestPath(final String requestHeader) {
        return requestHeader.split(NEW_LINE)[FIRST_LINE_INDEX].split(SPACE)[PATH_INDEX];
    }

    private String makeFilePath(final String requestHeader, final String prefix) {
        final String requestPath = extractRequestPath(requestHeader);
        final String pathEnd = (requestPath.endsWith("/") || "".equals(requestPath)) ? "index.html" : "";
        return prefix + requestPath + pathEnd;
    }

    private byte[] readBody(final String requestHeader) throws IOException, URISyntaxException, NullPointerException {
        try {
            return FileIoUtils.loadFileFromClasspath(makeFilePath(requestHeader, STATIC_PATH));
        } catch (IOException | URISyntaxException | NullPointerException e) {
            return FileIoUtils.loadFileFromClasspath(makeFilePath(requestHeader, TEMPLATES_PATH));
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
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
