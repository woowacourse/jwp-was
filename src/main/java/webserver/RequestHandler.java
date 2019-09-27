package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestParser;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseParser;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    public RequestHandler(final Socket connectionSocket) {
        this.connection = connectionSocket;
        this.httpRequest = new HttpRequest();
        this.httpResponse = new HttpResponse();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}/. ", connection.getInetAddress(),
                connection.getPort());

        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = connection.getOutputStream()) {
            receiveRequest(inputStream);
            sendResponse(outputStream);
        } catch (IOException e) {
            logger.error("서버 에러 " + e.getMessage());
        }
    }

    private void receiveRequest(final InputStream inputStream) {
        try {
            HttpRequestParser.parse(inputStream, httpRequest);
            Dispatcher.dispatch(httpRequest, httpResponse);
        } catch (RuntimeException e) {
            // TODO : 에러에 맞는 response 보내기
            logger.error("에러 메시지 : " + e.getMessage());
            httpResponse.redirect("/404.html");
        }
    }

    private void sendResponse(final OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        HttpResponseParser.send(dataOutputStream, httpResponse);
    }
}
