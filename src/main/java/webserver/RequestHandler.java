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

import model.User;
import utils.FileIoUtils;
import utils.IOUtils;
import utils.RedirectUrl;
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
            byte[] body = FileIoUtils.loadFileFromClasspath(FILE_PATH + url);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private String readRequest(BufferedReader br) throws IOException {
        String line = br.readLine();
        String firstLine = line;
        String url = RequestUtils.extractUrl(firstLine);
        int contentLength = 0;
        if (!url.endsWith(".html")) {
            url = RedirectUrl.findRedirectUrl(url).getRedirectUrl();
        }
        while (!EMPTY.equals(line) && line != null) {
            logger.debug("header : {}", line);
            if (line.startsWith("Content-Length")) {
                contentLength = Integer.parseInt(line.split(" ")[1]);
            }
            line = br.readLine();
        }

        if (RequestUtils.isPost(firstLine)) {
            String body = IOUtils.readData(br, contentLength);
            User user = RequestUtils.extractUser(body);
            logger.debug(user.toString());
        }
        return url;
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
