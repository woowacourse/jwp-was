package controller;

import http.request.Request;
import http.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;

import static http.ContentType.HTML;

public class IndexController extends AbstractController {
    @Override
    protected void doGet(Request request, Response response) throws IOException, URISyntaxException {
        response.ok("/index.html", HTML.getContentType());
    }
}
