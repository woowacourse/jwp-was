package controller;

import static http.ResponseHeader.*;
import static webserver.RequestHandler.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import http.ContentType;
import http.Request;
import utils.Directory;
import utils.FileIoUtils;

public class StaticController implements Controller {
    @Override
    public void service(Request request, DataOutputStream dos) throws IOException, URISyntaxException {
        String requestUrl = request.getRequestLine().getUrl();

        byte[] body = FileIoUtils.loadFileFromClasspath(Directory.STATIC.getDirectory() + requestUrl);
        ContentType contentType = ContentType.of(requestUrl);
        response200Header(dos, body.length, contentType.getContentType());
        responseBody(dos, body);
    }
}
