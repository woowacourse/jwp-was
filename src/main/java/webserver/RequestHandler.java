package webserver;

import http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import utils.FileIoUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

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
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            DataOutputStream dos = new DataOutputStream(out);
            router(br, dos); //TODO router 보다는 route가 명시적이다
        } catch (IOException | URISyntaxException | NullPointerException e) {
            logger.error(e.getMessage());
        }
    }

    private void router(BufferedReader br, DataOutputStream dos) throws IOException, URISyntaxException {
        RequestLine requestLine = new RequestLine(br);
        RequestHeader requestHeader = new RequestHeader(br);

        //TODO 좀 더 확장성있게 설계(http method는 고정값이라서 interface로 추출), path 처리해주는 클래스 안에 http method 처리해주는 메소드 넣으면 어떨까? -h
        if (requestLine.isMethodEqualTo("GET")) { //TODO 지원하지 않는 메소드로 요청이 왔을 때 예외처리 추가-j
            resolveGet(requestLine, requestHeader, dos);
        }
        if (requestLine.isMethodEqualTo("POST")) {
            RequestBody requestBody = new RequestBody(br, requestHeader.getContentLength());
            resolvePost(requestLine, requestHeader, requestBody, dos);
        }
    }

    private void resolveGet(RequestLine requestLine, RequestHeader requestHeader, DataOutputStream dos) throws IOException, URISyntaxException {
        //TODO 404, 405같은 상태코드를 생각해보자
        if (requestLine.isPathEqualTo("/")) {
            ResponseHeader.response302Header(dos, "/index.html");
        } else if (requestHeader.containsValueOf("accept","css")) {
            byte[] body = FileIoUtils.loadFileFromClasspath("./static" + requestLine.getPath());
            ResponseHeader.response200Header(dos, "text/css", body.length);
            ResponseBody.responseBody(dos, body);
        } else {
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + requestLine.getPath());
            ResponseHeader.response200Header(dos, "text/html;charset=utf-8", body.length);
            ResponseBody.responseBody(dos, body);
        }
    }

    private void resolvePost(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody, DataOutputStream dos) {
        if (requestLine.isPathEqualTo("/user/create")) {
            UserService userService = new UserService();
            userService.createUser(requestBody);
            ResponseHeader.response302Header(dos, "/index.html");
        }
    }
}
