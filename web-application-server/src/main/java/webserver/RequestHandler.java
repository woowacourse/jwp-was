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

import exception.DisabledEncodingException;
import exception.UnAuthenticationException;
import http.Cookie;
import http.HttpSession;
import http.HttpStatus;
import http.request.HttpRequest;
import http.request.SimpleHttpRequest;
import http.response.HttpResponse;
import servlet.DispatcherServlet;
import servlet.HttpServlet;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private DispatcherServlet dispatcherServlet;
    private Socket connection;

    public RequestHandler(DispatcherServlet dispatcherServlet, Socket connectionSocket) {
        this.dispatcherServlet = dispatcherServlet;
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
            HttpResponse httpResponse = new HttpResponse();
            try {
                HttpRequest httpRequest = SimpleHttpRequest.of(bufferedReader);
                logger.debug(System.lineSeparator() + httpRequest.toString());
                doDispatch(httpRequest, httpResponse);
            } catch (DisabledEncodingException | IndexOutOfBoundsException e) {
                httpResponse.internalServerError();
            } catch (UnAuthenticationException e) {
                httpResponse.setStatus(HttpStatus.MOVED_PERMANENTLY);
                httpResponse.addHeader("Location", "/user/login.html");
            } finally {
                httpResponse.send(dos);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void doDispatch(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            dispatcherServlet.service(httpRequest, httpResponse);
        } finally {
            HttpSession session = httpRequest.getSession();
            if (session.isNew()) {
                Cookie cookie = Cookie.createSessionIdCookie(session.getId());
                httpResponse.addCookie(cookie);
                session.toOld();
            }
        }
    }
}
