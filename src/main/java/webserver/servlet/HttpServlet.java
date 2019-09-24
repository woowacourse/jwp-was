package webserver.servlet;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.ResponseHeader;

import java.io.IOException;
import java.net.URISyntaxException;

public interface HttpServlet {
    HttpResponse run(HttpRequest httpRequest) throws IOException, URISyntaxException;

    default HttpResponse error(HttpStatus status, String errorMessage) {
        byte[] body = errorMessage.getBytes();
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setContentLegthAndType(body.length, "text/html;charset=utf-8");
        return new HttpResponse(status, responseHeader, body);
    }
}
