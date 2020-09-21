package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FormatUtils;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String ENCODING = "UTF-8";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, ENCODING));
            HttpResponse httpResponse = handle(new HttpRequest(bufferedReader));
            readResponse(httpResponse, new DataOutputStream(out));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void readResponse(HttpResponse httpResponse, DataOutputStream dos) {
        try {
            String responseLine = FormatUtils.formatResponseLine(httpResponse.getHttpResponseLine());
            dos.writeBytes(responseLine);

            for (Map.Entry<String, String> entry : httpResponse.getHttpResponseHeader().getHeaders().entrySet()) {
                dos.writeBytes(FormatUtils.formatHeader(entry));
            }

            if (httpResponse.hasBody()) {
                dos.writeBytes("\r\n");
                dos.write(httpResponse.getBody(), 0, httpResponse.getBody().length);
            }
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public HttpResponse handle(HttpRequest httpRequest) {
        return HandlerMapping.mapping(httpRequest);
    }
}