package webserver;

import static com.google.common.base.Charsets.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestParser;
import webserver.http.response.HttpResponse;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connectionSocket;
    private final RequestProcessor requestProcessor;

    public RequestHandler(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
        this.requestProcessor = new RequestProcessor();
    }

    public RequestHandler(Socket connectionSocket, RequestProcessor requestProcessor) {
        this.connectionSocket = connectionSocket;
        this.requestProcessor = requestProcessor;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connectionSocket.getInetAddress(),
                connectionSocket.getPort());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                connectionSocket.getInputStream(), UTF_8));
             DataOutputStream outputStream = new DataOutputStream(connectionSocket.getOutputStream())) {
            HttpRequest request = HttpRequestParser.parse(reader);
            HttpResponse response = requestProcessor.response(request);
            ResponseSender.send(outputStream, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
