package webserver;

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

import http.HttpSession;
import http.request.HttpRequest;
import http.request.SimpleHttpRequest;
import http.response.HttpResponse;
import servlet.DispatcherServlet;
import servlet.HttpServlet;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (
            InputStream inputStream = connection.getInputStream();
            OutputStream outputStream = connection.getOutputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            DataOutputStream dos = new DataOutputStream(outputStream);
        ) {
            HttpRequest httpRequest = SimpleHttpRequest.of(bufferedReader);
            logger.debug(System.lineSeparator() + httpRequest.toString());
            HttpResponse httpResponse = new HttpResponse();
            doDispatch(dos, httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void doDispatch(DataOutputStream dos, HttpRequest httpRequest, HttpResponse httpResponse) throws
        IOException {
        try {
            HttpServlet dispatcherServlet = new DispatcherServlet();
            dispatcherServlet.service(httpRequest, httpResponse);
        } finally {
            // TODO: 2020/11/21 나중에 분리 생각하기
            HttpSession session = httpRequest.getSession();
            httpResponse.setCookie("JSESSIONID=" + session.getId() + "; Path=/");
            httpResponse.send(dos);
        }
    }
}
