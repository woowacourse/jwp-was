package controller;

import exception.IllegalRequestException;
import http.request.Request;
import http.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;

import static http.ContentType.HTML;

public class IndexController extends AbstractController {
    @Override
    public void doGet(Request request, Response response) throws IOException, URISyntaxException {
        response.ok("/index.html", HTML.getContentType());
    }

    @Override
    public void doPost(Request request, Response response) throws NoSuchMethodException, IllegalRequestException {

    }
}
