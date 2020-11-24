package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.request.HttpHeader;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestFactory;
import webserver.http.response.HttpResponse;
import webserver.http.session.HttpSession;
import webserver.http.session.HttpSessionFactory;
import webserver.servlet.HttpServlet;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequestFactory()
                    .create(new BufferedReader(new InputStreamReader(in)));
            if (httpRequest.getHeader().hasSession()) {
                HttpResponse response = new HttpResponse(new HttpHeader(new HashMap<>()),
                        new DataOutputStream(out));
                HttpServlet.getInstance().doDispatch(httpRequest, response);
                return;
            }
            HttpSession session = new HttpSessionFactory().createWithSessionId();
            HttpResponse response = new HttpResponse(HttpHeader.withSession(session),
                    new DataOutputStream(out));
            HttpServlet.getInstance().doDispatch(httpRequest, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
