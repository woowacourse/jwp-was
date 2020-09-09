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
import webserver.domain.RequestBody;
import webserver.domain.RequestHeader;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(in, StandardCharsets.UTF_8)));
            RequestHeader requestHeader = new RequestHeader(IOUtils.readHeader(bufferedReader));
            RequestBody requestBody = new RequestBody(
                IOUtils.readBody(bufferedReader, requestHeader.findContentLength()));

            byte[] responseBody = {};
            if (requestHeader.isTemplate()) {
                responseBody = FileIoUtils.loadFileFromRequest(requestHeader.getPath());
            } else {
                if (requestHeader.equalPath("/user/create")) {
                    Map<String, String> params = requestBody.getParams();
                    User newUser = new User(params.get("userId"), params.get("password"), params.get("name"),
                        params.get("email"));
                }
            }
            DataOutputStream dos = new DataOutputStream(out);

            response200Header(dos, responseBody.length);
            responseBody(dos, responseBody);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
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
