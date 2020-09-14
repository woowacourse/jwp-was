package controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.FileIoUtils;
import webserver.RequestHandler;

public class PageController {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final int PATH_INDEX = 1;
    private static final int METHOD_INDEX = 0;

    public static void getIndexPage(final OutputStream out, final String[] request) throws
            IOException, URISyntaxException {
        if(!"GET".equals(request[METHOD_INDEX])) {
            return;
        }

        String uri = request[PATH_INDEX];

        if(request[PATH_INDEX].endsWith(".html")) {
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + uri);
            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        }  else {
            String[] headerToken = request[PATH_INDEX].split("\\.");
            byte[] body = FileIoUtils.loadFileFromClasspath("./static" + uri);
            DataOutputStream dos = new DataOutputStream(out);
            response200StaticResourcesHeader(dos, body.length, headerToken[headerToken.length-1]);
            responseBody(dos, body);
        }
    }

    private static void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void response200StaticResourcesHeader(DataOutputStream dos, int lengthOfBodyContent, String staticValue) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/"+staticValue+";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
