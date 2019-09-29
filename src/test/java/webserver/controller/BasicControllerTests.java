package webserver.controller;

import webserver.controller.request.HttpRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BasicControllerTests {
    private String testDirectory = "./src/test/resources/";

    protected HttpRequest httpRequest;

    protected void makeRequest(String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + fileName));
        httpRequest = new HttpRequest(inputStream);
    }
}
