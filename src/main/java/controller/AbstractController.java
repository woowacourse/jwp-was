package controller;

import static http.request.RequestMethod.*;

import java.io.IOException;
import java.net.URISyntaxException;

import http.request.Request;
import http.response.Response;

public abstract class AbstractController implements Controller {

    @Override
    public void service(Request request, Response response) throws
            IOException,
            URISyntaxException,
            NoSuchMethodException {
        if (request.isMethod(GET)) {
            doGet(request, response);
        } else if (request.isMethod(POST)) {
            doPost(request, response);
        }
    }

    public abstract void doGet(Request request, Response response) throws IOException, URISyntaxException;

    public abstract void doPost(Request request, Response response) throws NoSuchMethodException;
}
