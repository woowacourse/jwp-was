package controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import http.Request;

public interface Controller {
    void service(Request request, DataOutputStream dos) throws IOException, URISyntaxException;
}
