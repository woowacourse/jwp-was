package kr.wootecat.dongle.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import kr.wootecat.dongle.application.RequestProcessor;
import kr.wootecat.dongle.application.RequestProcessorFactory;
import kr.wootecat.dongle.model.http.request.HttpRequest;
import kr.wootecat.dongle.model.http.response.HttpResponse;
import kr.wootecat.dongle.view.HttpRequestReader;
import kr.wootecat.dongle.view.ResponseSender;

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
                connectionSocket.getInputStream(), Charsets.UTF_8));
             DataOutputStream outputStream = new DataOutputStream(connectionSocket.getOutputStream())) {
            HttpRequest request = HttpRequestReader.from(reader);
            RequestProcessor requestProcessor = RequestProcessorFactory.create();
            HttpResponse response = requestProcessor.response(request);
            ResponseSender.send(outputStream, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
