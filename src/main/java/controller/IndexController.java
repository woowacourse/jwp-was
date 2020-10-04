package controller;

import static http.ContentType.*;

import java.io.IOException;
import java.net.URISyntaxException;

import exception.IllegalRequestException;
import http.request.Request;
import http.response.Response;

public class IndexController extends AbstractController {
    @Override
    public void doGet(Request request, Response response) throws IOException, URISyntaxException {
        response.setHeader("Content-Type", HTML.getContentType() + ";charset=UTF-8");
        response.ok("/index.html");
    }

    @Override
    public void doPost(Request request, Response response) throws NoSuchMethodException, IllegalRequestException {

    }
}
