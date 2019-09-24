package webserver.servlet;

import utils.FileIoUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static utils.HttpRequestUtils.generateTemplateFilePath;

public class HomeServlet extends RequestServlet {
    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws IOException {
        try {
            String filePath = generateTemplateFilePath(httpRequest.getAbsPath() + "index.html");
            byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
            Map<String, Object> header = new HashMap<>();
            header.put("Content-Length", body.length);
            header.put("Content-Type", FileIoUtils.loadMIMEFromClasspath(filePath));
            return HttpResponse.ok(header, body);
        } catch (URISyntaxException e) {
            return HttpResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NullPointerException e) {
            return HttpResponse.error(HttpStatus.NOT_FOUND);
        }
    }
}

