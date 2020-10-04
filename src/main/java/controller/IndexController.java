package controller;

import static http.ContentType.*;

import java.io.IOException;
import java.net.URISyntaxException;

import http.request.Request;
import http.response.Response;

public class IndexController extends AbstractController {
    @Override
    public void doGet(Request request, Response response) throws IOException, URISyntaxException {
        response.ok("/index.html", HTML.getContentType());
    }

    @Override
    public void doPost(Request request, Response response) throws NoSuchMethodException {
        throw new NoSuchMethodException("지원하지 않은 메서드입니다.");
    }
}
