package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import webserver.support.*;

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
            Request request = readRequestUrl(in);
            RequestProcessor requestProcessor = new RequestProcessor();

            ResponseHeader responseHeader = requestProcessor.process(request);
            String absoluteUrl = PathHandler.path(responseHeader.getUrl());

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = FileIoUtils.loadFileFromClasspath(absoluteUrl);
            Response response = new Response(responseHeader, new ResponseBody(body));

            response.writeMessage(dos);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private Request readRequestUrl(InputStream in) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(inputStreamReader);
        RequestHeader header = new RequestHeader(IOUtils.parseData(br));

        if (header.get(RequestHeader.HTTP_METHOD).equals("POST")) {
            String body = IOUtils.readData(br, Integer.parseInt(header.get("content-length")));
            RequestBody requestBody = new RequestBody(body);
            return new Request(header, requestBody);
        }
        return new Request(header);
    }
}
