package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
            HttpResponse httpResponse = controlRequestAndResponse(HttpRequest.of(in));
            respondToHttpRequest(out, httpResponse);
        } catch (IOException | URISyntaxException | IllegalAccessException | InstantiationException e) {
            logger.error(e.getMessage());
        }
    }

    public HttpResponse controlRequestAndResponse(HttpRequest httpRequest) throws
        IOException,
        URISyntaxException,
        IllegalAccessException,
        InstantiationException {
        // todo : 1. 정적 컨텐츠 동적 컨텐츠 분리
        // todo : 2-1-1. 정적 컨텐츠는 static 안에서 파일 먼저 찾고 없으면 templates에서 찾음
        // todo : 2-1-2. 정적 컨텐츠는 확장자에 따른 contentType지정
        if (httpRequest.isForStaticContent()) {
            String path = httpRequest.getPath();
            byte[] body = FileIoUtils.loadFileFromClasspath(path);
            String contentType = "";
            if (path.endsWith(".html")) {
                contentType = "text/html;charset=utf-8";
            } else if (path.endsWith(".css")) {
                contentType = "text/css";
            } else if (path.endsWith(".js")) {
                contentType = "application/javascript";
            } else {
                contentType = "text/plain";
            }
            return HttpResponse.ok()
                .contentType(contentType)
                .contentLength(body.length)
                .body(body)
                .build();
        }

        if (httpRequest.isForDynamicContent()) {
            String path = httpRequest.getPath();
            Class<? extends Servlet> servletClass = controller.get(path);
            Servlet servlet = servletClass.newInstance();
            return servlet.service(httpRequest);
        }

        throw new AssertionError("HttpRequest는 정적 혹은 동적 컨텐츠 요청만 가능합니다.");
    }

    private void respondToHttpRequest(OutputStream out, HttpResponse httpResponse) {
        DataOutputStream dos = new DataOutputStream(out);
        httpResponse.respond(dos);
    }
}
