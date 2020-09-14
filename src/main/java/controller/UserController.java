package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.UserService;
import utils.IOUtils;
import webserver.RequestHandler;

public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final int URL_INDEX = 1;
    private static final int METHOD_INDEX = 0;


    public static void postSignIn (final OutputStream out, final BufferedReader bufferedReader, final String[] request,
            final HashMap<String, String> requestUrl) throws IOException {

        if (isPostSignIn(request)) {
            String userInfoUrl = IOUtils.readData(bufferedReader, Integer.parseInt(requestUrl.get("Content-Length")));
            UserService.signIn(userInfoUrl);
            logger.debug("userInfo: {}" + userInfoUrl);
            DataOutputStream dos = new DataOutputStream(out);
            response302Header(dos);
        }
    }

    private static boolean isPostSignIn(final String[] request) {
        return "POST".equals(request[METHOD_INDEX]) && request[URL_INDEX].startsWith("/user/create");
    }

    private static void response302Header(DataOutputStream dos) {

        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: /index.html\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
