package controller;

import java.io.IOException;
import java.net.URISyntaxException;

import http.ContentType;
import http.request.Request;
import http.response.Response;

public class ResourceController extends AbstractController {
    public void doGet(Request request, Response response) throws IOException, URISyntaxException {
        String requestUrl = request.getRequestLine().getUrl();

        ContentType contentType = ContentType.of(requestUrl);
        response.setHeader("Content-Type", contentType.getContentType() + ";charset=UTF-8");
        response.ok(requestUrl);
    }

    @Override
    public void doPost(Request request, Response response) throws NoSuchMethodException {
        throw new NoSuchMethodException("지원하지 않는 메소드입니다.");
    }
}
