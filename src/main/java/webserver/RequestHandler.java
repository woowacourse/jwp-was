package webserver;

import controller.ControllerContainer;
import http.request.HttpRequest;
import http.request.factory.HttpRequestFactory;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.RequestParser;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = connection.getOutputStream()) {

            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            HttpRequest httpRequest = HttpRequestFactory.create(RequestParser.lineParse(inputStream));
            HttpResponse httpResponse = new HttpResponse();

            logger.debug("RequestLine: {}", httpRequest.getHttpRequestStartLine().toString());

            if (httpRequest.isContainExtension()) {
                ControllerContainer.getController("/").service(httpRequest, httpResponse);
            } else {
                ControllerContainer.getController(httpRequest.getUri()).service(httpRequest, httpResponse);
            }

            dataOutputStream.writeBytes(httpResponse.getHttpResponseStatusLine().toString());
            dataOutputStream.writeBytes(httpResponse.getHttpResponseHeader().toString());

            if (httpResponse.getHttpResponseBody() != null) {
                responseBody(dataOutputStream, httpResponse.getHttpResponseBody().getBody());
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dataOutputStream, byte[] body) {
        try {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
