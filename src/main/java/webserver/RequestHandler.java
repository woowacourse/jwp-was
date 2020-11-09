package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestParser;
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
            try {
                final HandlerMappings handlerMappings = new HandlerMappings(
                        Arrays.asList(new StaticResourceHandlerMapping(),
                                new ServletHandlerMapping(ServletMapper.getInstance())));
                final HandlerMapping handler = handlerMappings.findHandler(request);
                handler.handle(request, response);
            } catch (RuntimeException e) {
                System.out.println("여기에서는 404, 500, 등 .... 에러가 잡히는 구간을 캐치해서 예외를 던질 예정입니다.");
            }
            ResponseSender.send(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
