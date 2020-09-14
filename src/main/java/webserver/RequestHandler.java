package webserver;

import controller.StaticFileController;
import controller.UserController;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import request.Method;
import response.HttpResponse;
import utils.IOUtils;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private UserController userController = new UserController();
    private StaticFileController staticFileController = new StaticFileController();

    RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(), connection.getPort());

        try (
            InputStream in = connection.getInputStream();
            OutputStream out = connection.getOutputStream();
            BufferedReader br = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8))
        ) {
            HttpRequest httpRequest = readRequest(br);

            if (httpRequest.isMethod(Method.POST) && httpRequest.isUriPath("/user/create")) {
                HttpResponse response = userController.createUser(httpRequest);
                DataOutputStream dos = new DataOutputStream(out);

                try {
                    dos.writeBytes(response.buildHeader());
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
                return;
            }
            HttpResponse response = staticFileController.findStaticFile(httpRequest);
            DataOutputStream dos = new DataOutputStream(out);

            try {
                dos.writeBytes(response.buildHeader());

                byte[] body = response.getBody();
                dos.write(body, 0, body.length);
                dos.flush();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpRequest readRequest(BufferedReader br) throws IOException {
        String requestHeader = IOUtils.readDataBeforeEmptyLine(br);
        String requestBody = "";

        if (HttpRequest.isExistRequestHeader(requestHeader, "Content-Length")) {
            int contentLength = Integer.parseInt(HttpRequest.findHeaderValue(
                requestHeader, "Content-Length"));
            requestBody = IOUtils.readData(br, contentLength);
        }
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());
        logger.debug("Receive HttpRequest\n{}\n{}", requestHeader, requestBody);

        return new HttpRequest(requestHeader, requestBody);
    }
}
