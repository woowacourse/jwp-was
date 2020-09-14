package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.PageController;
import controller.UserController;
import utils.IOUtils;
import utils.RequestUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final int PATH_INDEX = 1;

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HashMap<String, String> requestUrl = new HashMap<>();
            String line = bufferedReader.readLine();

            String[] request = RequestUtils.separateUrl(line);
            IOUtils.parseHeaderToken(bufferedReader,line,requestUrl);

            UserController.postSignIn(out, bufferedReader, request, requestUrl);
            PageController.getIndexPage(out, request);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
