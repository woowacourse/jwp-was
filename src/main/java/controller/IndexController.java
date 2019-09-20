package controller;

import model.Request;
import model.Response;

public class IndexController extends AbstractController {
    @Override
    void doPost(Request request, Response response) {

    }

    @Override
    void doGet(Request request, Response response) {
        String url = request.getUrl();
        String extension = url.substring(url.lastIndexOf(".") + 1);

        String classPath = "./templates" + url;

        if (!"html".equals(extension)) {
            classPath = "./static" + url;
        }

        response.response200(classPath);
    }
}
