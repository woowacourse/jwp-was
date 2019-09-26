package webserver.controller;

import http.request.Request;
import http.response.Response;
import webserver.support.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileController extends HttpController {

    private FileController() {
    }

    public static FileController getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    protected void doGet(Request request, Response response) throws IOException, URISyntaxException {
        String url = request.extractUrl();
        response.forward(new ModelAndView(url));
    }

    private static class LazyHolder {
        private static final FileController INSTANCE = new FileController();
    }
}
