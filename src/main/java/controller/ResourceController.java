package controller;

import http.request.Request;
import http.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceController extends AbstractController {
    public void doGet(Request request, Response response) throws IOException, URISyntaxException {
        String requestUrl = request.getPath();
        String contentType = request.getContentType();

        response.ok(requestUrl, contentType);
    }

    @Override
    public void doPost(Request request, Response response) throws NoSuchMethodException {
        throw new NoSuchMethodException("지원하지 않는 메소드입니다.");
    }
}
