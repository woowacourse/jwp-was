package kr.wootecat.dongle.core;

import static com.google.common.base.Charsets.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.wootecat.dongle.core.handler.HandlerMappingsFactory;
import kr.wootecat.dongle.http.request.HttpRequest;
import kr.wootecat.dongle.http.request.HttpRequestReader;
import kr.wootecat.dongle.http.response.HttpResponse;
import kr.wootecat.dongle.http.session.SessionStorage;
import kr.wootecat.dongle.http.session.SessionValidator;
import kr.wootecat.dongle.utils.IdGeneratorFactory;

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

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                connectionSocket.getInputStream(), UTF_8));
             DataOutputStream outputStream = new DataOutputStream(connectionSocket.getOutputStream())) {
            HttpRequest request = HttpRequestReader.parse(reader);
            RequestProcessor requestProcessor = new RequestProcessor(
                    HandlerMappingsFactory.create(),
                    new SessionValidator(SessionStorage.ofEmpty(), IdGeneratorFactory.create())
            );
            HttpResponse response = requestProcessor.response(request);
            ResponseSender.send(outputStream, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
