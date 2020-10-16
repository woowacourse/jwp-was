package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import utils.FileIoUtils;
import utils.StringUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String RESOURCE_PATH = "./templates";
    private static final String STATIC_PATH = "./static";
    private static final String HTML_ACCEPT = "text/html";
    private static final String ACCEPT = "Accept";
    private static final String HTML_CONTENT_TYPE = "text/html;charset=utf-8";
    private static final String CSS_CONTENT_TYPE = "text/css";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.from(
                    new BufferedReader(new InputStreamReader(in)));
            HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseByRequestMethod(DataOutputStream dos, HttpRequest httpRequest) throws
            IOException, URISyntaxException {
        if (httpRequest.isPost()) {
            responseCreateUser(dos, httpRequest);
            return;
        }
        responsePage(dos, httpRequest);
    }

    private void responseCreateUser(DataOutputStream dos, HttpRequest httpRequest) {
        if (httpRequest.getPath().contains("/user/create")) {
            DataBase.addUser(User.from(StringUtils.readParameters(httpRequest.getBody())));
            response302Header(dos, "/index.html");
        }
    }

    private void responsePage(DataOutputStream dos, HttpRequest httpRequest) throws
            IOException,
            URISyntaxException {
        if (httpRequest.getAttribute(ACCEPT).contains(HTML_ACCEPT)) {
            responsePageByContentType(dos, httpRequest, RESOURCE_PATH, HTML_CONTENT_TYPE);
            return;
        }
        responsePageByContentType(dos, httpRequest, STATIC_PATH, CSS_CONTENT_TYPE);
    }

    private void responsePageByContentType(DataOutputStream dos, HttpRequest httpRequest,
            String resourcePath, String s) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(
                resourcePath + httpRequest.getPath());
        response200Header(dos, s, body.length);
        responseBody(dos, body);
    }
}
