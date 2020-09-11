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
import java.util.Map;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import utils.StaticFileType;
import webserver.domain.HttpRequestBody;
import webserver.domain.HttpRequestHeader;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String NEW_LINE = System.lineSeparator();

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream();
            BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(in, StandardCharsets.UTF_8)))) {

            HttpRequestHeader httpRequestHeader = new HttpRequestHeader(IOUtils.readHeader(bufferedReader));
            HttpRequestBody httpRequestBody = new HttpRequestBody(
                IOUtils.readBody(bufferedReader, httpRequestHeader.findContentLength()));

            byte[] responseBody = {};
            DataOutputStream dos = new DataOutputStream(out);
            if (httpRequestHeader.isStaticFile()) {
                responseBody = FileIoUtils.loadFileFromRequest(httpRequestHeader.findExtension(),
                    httpRequestHeader.getPath());
                response200Header(dos, httpRequestHeader.findExtension(), responseBody.length);
            } else {
                if (httpRequestHeader.hasEqualPathWith("/user/create")) {
                    Map<String, String> params = httpRequestBody.getRequestParams();
                    User newUser = new User(params.get("userId"), params.get("password"), params.get("name"),
                        params.get("email"));

                    response302Header(dos, "/index.html");
                }
            }

            responseBody(dos, responseBody);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void response200Header(DataOutputStream dos, StaticFileType staticFileType, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK " + NEW_LINE);
            dos.writeBytes("Content-Type: " + staticFileType.getContentType() + ";charset=utf-8" + NEW_LINE);
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + NEW_LINE);
            dos.writeBytes(NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND " + NEW_LINE);
            dos.writeBytes("Location: " + redirectUrl + NEW_LINE);
            dos.writeBytes(NEW_LINE);
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
