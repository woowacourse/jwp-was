package http.controller;

import http.RequestBody;
import http.RequestHeader;
import http.ResponseBody;
import http.ResponseHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class RawFileController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private String filePath;

    public RawFileController(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void get(DataOutputStream dos, RequestHeader requestHeader) {
        try {
            if (requestHeader.containsValueOf("accept", "css")) {
                byte[] body = FileIoUtils.loadFileFromClasspath("./static" + filePath);
                ResponseHeader.response200Header(dos, "text/css", body.length);
                ResponseBody.responseBody(dos, body);
            } else {
                byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + filePath);
                ResponseHeader.response200Header(dos, "text/html;charset=utf-8", body.length);
                ResponseBody.responseBody(dos, body);
            }
        } catch (NullPointerException e) {
            ResponseHeader.response404Header(dos);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void post(DataOutputStream dos, RequestHeader requestHeader, RequestBody requestBody) {
        ResponseHeader.response405Header(dos);
    }
}
