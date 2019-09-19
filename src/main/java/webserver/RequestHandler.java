package webserver;

import http.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBody;
import http.response.HttpResponseHeader;
import http.response.HttpStatusLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = connection.getOutputStream()) {
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            HttpRequest httpRequest = new HttpRequest(bufferedReader);
            HttpResponse httpResponse = makeResponse(httpRequest);

            dataOutputStream.writeBytes(httpResponse.getHttpStatusLine().toString());
            dataOutputStream.writeBytes(httpResponse.getHttpResponseHeader().toString());
            responseBody(dataOutputStream, httpResponse.getHttpResponseBody().getBody());
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpResponse makeResponse(HttpRequest httpRequest) throws IOException, URISyntaxException {
        HttpMethod method = httpRequest.getMethod();
        String uri = httpRequest.getUri();

        if (method.equals(HttpMethod.GET)) {
            HttpStatusLine httpStatusLine = new HttpStatusLine("HTTP/1.1 200 OK \r\n");
            HttpResponseBody httpResponseBody = new HttpResponseBody(uri);
            HttpResponseHeader httpResponseHeader = new HttpResponseHeader(
                    "Content-Type: text/html;charset=utf-8\r\n"
                            + "Content-Length: " + httpResponseBody.getBodyLength());
            HttpResponse httpResponse = new HttpResponse(httpStatusLine, httpResponseHeader, httpResponseBody);
            return httpResponse;
        }

        return null;
    }

    private void response200Header(DataOutputStream dataOutputStream, int lengthOfBodyContent) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
            dataOutputStream.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dataOutputStream.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dataOutputStream.writeBytes("\r\n");
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
