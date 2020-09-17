package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.FileIoUtils;
import webserver.domain.request.HttpRequest;
import webserver.domain.response.HttpResponse;
import webserver.servlet.Servlet;
import webserver.servlet.UserCreate;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final Map<String, Class<? extends Servlet>> controller;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.controller = new HashMap<>();
        controller.put("/user/create", UserCreate.class);
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = generateHttpRequest(in);
            HttpResponse httpResponse = controlRequestAndResponse(httpRequest);
            respondToHttpRequest(out, httpResponse);
        } catch (IOException | URISyntaxException | IllegalAccessException | InstantiationException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpRequest generateHttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        return HttpRequest.of(br);
    }

    public HttpResponse controlRequestAndResponse(HttpRequest httpRequest) throws
        IOException,
        URISyntaxException,
        IllegalAccessException,
        InstantiationException {
        if (httpRequest.isForStaticContent()) {
            String path = httpRequest.getPath();
            byte[] body = FileIoUtils.loadFileFromClasspath(path);
            return HttpResponse.of("200", body);
        }

        if (httpRequest.isForDynamicContent()) {
            String path = httpRequest.getPath();
            Class<? extends Servlet> servletClass = controller.get(path);
            Servlet servlet = servletClass.newInstance();
            Map<String, String> parameters = httpRequest.getParameters();
            servlet.service(parameters);
            byte[] body = FileIoUtils.loadFileFromClasspath(servlet.getResourcePathToRedirect());
            return HttpResponse.of("302", body);
        }

        throw new AssertionError("HttpRequest는 정적 혹은 동적 컨텐츠 요청만 가능합니다.");
    }

    private void respondToHttpRequest(OutputStream out, HttpResponse httpResponse) {
        DataOutputStream dos = new DataOutputStream(out);
        httpResponse.respond(dos);
    }
}
