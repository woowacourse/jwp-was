package web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.HttpStatus;

public class UserController extends AbstractController {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        User user = new User("1", "password", "name", "email@eamil.com");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(user);
        byte[] body = bos.toByteArray();
        httpResponse.setHttpStatus(HttpStatus.OK);
        httpResponse.forward(body);
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        User user = new User(httpRequest.getParameter());
        httpResponse.setHttpStatus(HttpStatus.FOUND);
        httpResponse.sendRedirect("/index.html");
    }

    @Override
    public void doPut(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    @Override
    public void doDelete(HttpRequest httpRequest, HttpResponse httpResponse) {

    }
}
