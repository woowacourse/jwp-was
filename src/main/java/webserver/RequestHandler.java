package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.HandlerMapping;
import web.HttpHeader;
import web.controller.Controller;
import web.cookie.CookieOption;
import web.request.HttpRequest;
import web.response.HttpResponse;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            HttpRequest httpRequest = new HttpRequest(br);
            HttpResponse httpResponse = new HttpResponse(dos, HttpHeader.ofResponse());
            httpResponse.getCookies().add("JSESSIONID", httpRequest.getSession().getId(), CookieOption.PATH, "/");

            Controller controller = HandlerMapping.find(httpRequest);
            controller.doService(httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
