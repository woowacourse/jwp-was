package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import utils.FileIoUtils;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestParser;
import webserver.http.request.MimeMatcher;
import webserver.http.response.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connectionSocket;

    public RequestHandler(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connectionSocket.getInetAddress(),
                connectionSocket.getPort());

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                connectionSocket.getInputStream(), Charsets.UTF_8));
             DataOutputStream dos = new DataOutputStream(connectionSocket.getOutputStream())) {
            final HttpRequest request = HttpRequestParser.parse(br);
            final HttpResponse response = new HttpResponse();

            // 정적, 동적을 구분해주고, 이를 분기로 처리해주는 친구를 만들어야 겠네.
            // 정적 처리 해주는 구간
            if (request.isStaticResourceRequest()) {
                final MimeMatcher matcher = MimeMatcher.of(request.getPath());
                final byte[] body = FileIoUtils.loadFileFromClasspath(matcher.getFilePosition() + request.getPath());
                response200Header(dos, body.length, matcher.getMimeType());
                responseBody(dos, body);
                return;
            }

            // 동적 처리 해주는 구간
            ServletMapper servletMapper = ServletMapper.getInstance();
            final HttpServlet servlet = servletMapper.get(request.getPath());
            servlet.doService(request, response);
            response200Header(dos, 0, "jordy");
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("여기에서는 404, 500, 등 .... 에러가 잡히는 구간을 캐치해서 예외를 던질 예정입니다.");
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String mimeType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + mimeType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
